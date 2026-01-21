package com.example.smartsplitter.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;

import com.example.smartsplitter.data.ExpenseWithSplits;
import com.example.smartsplitter.data.Group;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PdfUtil {

    public static void exportGroupExpenses(Context context, Group group, List<ExpenseWithSplits> expenses) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        // Page Configuration
        int pageWidth = 595;
        int pageHeight = 842; // A4 size roughly
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        // Styles
        titlePaint.setTextSize(18);
        titlePaint.setFakeBoldText(true);
        titlePaint.setColor(Color.BLACK);

        paint.setTextSize(12);
        paint.setColor(Color.DKGRAY);

        int startX = 40;
        int startY = 50;
        int yPos = startY;

        // Header
        canvas.drawText("Group: " + (group != null ? group.groupName : "Unknown"), startX, yPos, titlePaint);
        yPos += 30;
        canvas.drawText(
                "Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date()),
                startX, yPos, paint);
        yPos += 40;

        // Table Header
        paint.setFakeBoldText(true);
        paint.setColor(Color.BLACK);
        canvas.drawText("Date", startX, yPos, paint);
        canvas.drawText("Description", startX + 100, yPos, paint);
        canvas.drawText("Amount", startX + 350, yPos, paint);
        canvas.drawText("Cat", startX + 450, yPos, paint);
        paint.setFakeBoldText(false);
        paint.setColor(Color.DKGRAY);
        yPos += 20;

        // Separator line
        Paint linePaint = new Paint();
        linePaint.setColor(Color.LTGRAY);
        linePaint.setStrokeWidth(1);
        canvas.drawLine(startX, yPos - 15, pageWidth - startX, yPos - 15, linePaint);

        // Content
        if (expenses != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
            for (ExpenseWithSplits expense : expenses) {
                if (yPos > pageHeight - 50) {
                    // New Page needed - Simplified for MVP: Just stop or basic logic
                    // For now, if we exceed one page, we just truncate or need loop pagination.
                    // Sticking to 1 page for demo.
                    break;
                }

                String date = sdf.format(new Date(expense.expense.expenseDate));
                String desc = expense.expense.description;
                if (desc.length() > 30)
                    desc = desc.substring(0, 27) + "...";
                String amount = String.format(Locale.getDefault(), "%s %.2f", expense.expense.currency,
                        expense.expense.totalAmount);
                String cat = expense.expense.category;

                canvas.drawText(date, startX, yPos, paint);
                canvas.drawText(desc, startX + 100, yPos, paint);
                canvas.drawText(amount, startX + 350, yPos, paint);
                canvas.drawText(cat, startX + 450, yPos, paint);

                yPos += 25;
            }
        }

        pdfDocument.finishPage(myPage);

        // Save file
        String fileName = "Expenses_" + (group != null ? group.groupName.replaceAll("\\s+", "_") : "Group") + "_"
                + System.currentTimeMillis() + ".pdf";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "PDF saved to Downloads/" + fileName, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }
}
