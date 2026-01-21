package com.example.smartsplitter.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import com.example.smartsplitter.data.ExpenseWithSplits;
import com.example.smartsplitter.data.GroupWithMembers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PdfGenerator {

    public static File generateGroupReport(Context context, GroupWithMembers group, 
                                           List<ExpenseWithSplits> expenses, 
                                           Map<String, BalanceCalculator.MemberBalance> balances) {
        
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // A4
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        int y = 50;
        int x = 50;

        // Title
        paint.setTextSize(24);
        paint.setColor(Color.BLACK);
        paint.setFakeBoldText(true);
        canvas.drawText("Expense Report: " + group.group.groupName, x, y, paint);
        y += 40;

        paint.setTextSize(14);
        paint.setFakeBoldText(false);
        canvas.drawText("Generated on: " + DateFormat.getDateInstance().format(new Date()), x, y, paint);
        y += 30;
        canvas.drawText("Currency: " + group.group.currency, x, y, paint);
        y += 50;

        // Balances Section
        paint.setTextSize(18);
        paint.setFakeBoldText(true);
        canvas.drawText("Member Balances", x, y, paint);
        y += 30;

        paint.setTextSize(14);
        paint.setFakeBoldText(false);
        for (BalanceCalculator.MemberBalance bal : balances.values()) {
            String status = bal.netBalance >= 0 ? "gets back" : "owes";
            String line = String.format("%s %s %.2f", bal.displayName, status, Math.abs(bal.netBalance));
            canvas.drawText(line, x, y, paint);
            y += 20;
        }
        y += 40;

        // Expenses Section
        paint.setTextSize(18);
        paint.setFakeBoldText(true);
        canvas.drawText("Detailed Expenses", x, y, paint);
        y += 30;

        paint.setTextSize(12);
        paint.setFakeBoldText(false);
        
        // Header
        canvas.drawText("Date", x, y, paint);
        canvas.drawText("Description", x + 80, y, paint);
        canvas.drawText("Amount", x + 350, y, paint);
        y += 20;
        
        // List
        for (ExpenseWithSplits exp : expenses) {
            if (y > 780) { // New Page needed
                document.finishPage(page);
                page = document.startPage(pageInfo);
                canvas = page.getCanvas();
                y = 50;
            }
            
            String date = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(exp.expense.expenseDate));
            String desc = exp.expense.description;
            // Truncate desc if too long
            if (desc.length() > 30) desc = desc.substring(0, 27) + "...";
            
            String amount = String.format("%.2f", exp.expense.totalAmount);

            canvas.drawText(date, x, y, paint);
            canvas.drawText(desc, x + 80, y, paint);
            canvas.drawText(amount, x + 350, y, paint);
            y += 20;
        }

        document.finishPage(page);

        // Save
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "Report_" + group.group.groupName.replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + ".pdf";
        File file = new File(downloadsDir, fileName);

        try {
            document.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            file = null;
        }

        document.close();
        return file;
    }
}
