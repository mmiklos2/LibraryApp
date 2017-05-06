package utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dataobjects.DetailedBook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFBuilder {


    private static Font fontTimes = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

    private File f = new File("AllBooks.pdf");
    private int counter = 0;

    private List<DetailedBook> dba = new ArrayList<DetailedBook>() {
    };

    public PDFBuilder(List<DetailedBook> _dba) {
        dba = _dba;
    }

    private void createTable(Section sec)
            throws BadElementException {

        PdfPTable table = new PdfPTable(6);

        PdfPCell cell = new PdfPCell(new Phrase("Book ISBN "));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Book Title"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Book Author"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Book Publisher"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Year of publication"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Book Genre"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        table.setHeaderRows(1);

        for (int i = 0; i < dba.size(); i++) {
            table.addCell(String.valueOf(dba.get(i).getBook_isbn().getValue()));
            table.addCell(String.valueOf(dba.get(i).getBook_title().getValue()));
            table.addCell(String.valueOf(dba.get(i).getAuthor().getValue()));
            table.addCell(String.valueOf(dba.get(i).getPublisher_name().getValue()));
            table.addCell(String.valueOf(dba.get(i).getBook_publisher_year().getValue()));
            table.addCell(String.valueOf(dba.get(i).getBook_genre().getValue()));

        }
        sec.add(table);
    }

    private void addMetaData(Document document) {
        document.addTitle("All_Books");
        document.addSubject("Books in Library");
        document.addKeywords("Java, PDF");
        document.addAuthor("ZG4 Team");
        document.addCreator("ZG4 Team");
    }

    private void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Books in Library", fontTimes);
        anchor.setName("Books in Library");
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph();
        Section sec = catPart.addSection(subPara);

        createTable(sec);
        document.add(catPart);
    }

    public void populatePDF() {

        if (f.exists()) {

            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("AllBooks_Copy" + "(" + counter + ")" + ".pdf"));
                document.open();
                addMetaData(document);
                addContent(document);
                document.close();
                f = new File("AllBooks_Copy" + "(" + counter + ")" + ".pdf");
                counter++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("AllBooks.pdf"));
                document.open();
                addMetaData(document);
                addContent(document);
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
