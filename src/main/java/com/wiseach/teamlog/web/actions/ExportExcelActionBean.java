package com.wiseach.teamlog.web.actions;

import com.mysql.jdbc.StringUtils;
import com.wiseach.teamlog.db.WorkLogDBHelper;
import com.wiseach.teamlog.utils.DateUtils;
import com.wiseach.teamlog.utils.TeamlogUtils;
import com.wiseach.teamlog.web.resolutions.JsonResolution;
import com.wiseach.teamlog.web.security.UserAuthProcessor;
import net.sourceforge.stripes.action.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: Arlen Tan
 * 12-8-9 下午1:45
 */
@UrlBinding("/worklog/export/{period}/{people}")
public class ExportExcelActionBean extends BaseActionBean {

    @DefaultHandler
    public Resolution view() {
        String message = TeamlogUtils.checkWorklogConditions(period,people);

        if (StringUtils.isNullOrEmpty(message)) {
            final Map<String, DateTime> datePeriod = TeamlogUtils.getSplitDate(period);
            final List<Long> userIdList = TeamlogUtils.minusList(TeamlogUtils.getSplitId(people),WorkLogDBHelper.getSharedWithMe(getUserId()));

            final List<Map<String, Object>> worklogs = WorkLogDBHelper.getWorkLogExportData(datePeriod.get("start").toDate(), datePeriod.get("end").toDate(), userIdList.toArray());
            if (worklogs.size()>0) {
                return new StreamingResolution("application/vnd.ms-excel") {
                    @Override
                    protected void stream(HttpServletResponse response) throws Exception {
                        FileInputStream excelTemp = new FileInputStream(getRealPath("/") + "WEB-INF/classes/teamlog.xlsx");
                        XSSFWorkbook teamlogExcel = new XSSFWorkbook(excelTemp);
                        excelTemp.close();

                        XSSFSheet sheet = teamlogExcel.getSheetAt(0);
                        int rowCount=1;
                        XSSFRow row;
                        XSSFCell cell;
                        CellStyle cellStyle = teamlogExcel.createCellStyle();
                        cellStyle.setDataFormat(teamlogExcel.getCreationHelper().createDataFormat().getFormat("yyyy-mm-dd"));
                        for (Map<String, Object> worklog : worklogs) {
                            row = sheet.createRow(rowCount++);
                            cell = row.createCell(0);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue((Date) worklog.get("workDate"));
                            row.createCell(1).setCellValue(worklog.get("stTime").toString());
                            row.createCell(2).setCellValue(worklog.get("edTime").toString());
                            row.createCell(3).setCellValue(TeamlogUtils.convertDecimal(worklog.get("hours")));
                            row.createCell(4).setCellValue((String)worklog.get("description"));
                            row.createCell(5).setCellValue((String)worklog.get("staff"));
                            row.createCell(6).setCellValue((String)worklog.get("tag"));
                            row.createCell(7).setCellValue((Integer)worklog.get("nice"));
                            row.createCell(8).setCellValue((Integer)worklog.get("comments"));
                        }

                        teamlogExcel.write(response.getOutputStream());
                    }
                }.setFilename("teamlog_export_"+ DateUtils.formatDateTime(new Date()) +".xlsx");
            } else {
                setRequestAttribute(ERROR_DESCRIPTION_KEY,"worklog.error.export.empty");
                return ViewHelper.getStandardErrorBoxResolution();
            }
        } else {
            setRequestAttribute(ERROR_DESCRIPTION_KEY,message);
            return ViewHelper.getStandardErrorBoxResolution();
        }
    }

    public String period,people;

}
