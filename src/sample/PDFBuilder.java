package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFBuilder {
    private static String FILE = "src\\";

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    File f= new File( "src\\AllBooks.pdf");
    int counter=0;
    private List<DetailedBook> dba=new ArrayList<DetailedBook>() {
    };
    public PDFBuilder(List<DetailedBook> _dba){
        dba=_dba;
    }
   private  void createTable(Section subCatPart)
            throws BadElementException {

        PdfPTable table = new PdfPTable(6);

        PdfPCell c1 = new PdfPCell(new Phrase("Book ISBN "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Book Title"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

       c1 = new PdfPCell(new Phrase("Book Author"));
       c1.setHorizontalAlignment(Element.ALIGN_CENTER);
       table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Book Publisher"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

       c1 = new PdfPCell(new Phrase("Year of publication"));
       c1.setHorizontalAlignment(Element.ALIGN_CENTER);
       table.addCell(c1);

       c1 = new PdfPCell(new Phrase("Book Genre"));
       c1.setHorizontalAlignment(Element.ALIGN_CENTER);
       table.addCell(c1);

        table.setHeaderRows(1);

       for (int i=0;i<dba.size();i++){
           table.addCell(String.valueOf(dba.get(i).getBook_isbn().getValue()));
           table.addCell(String.valueOf(dba.get(i).getBook_title().getValue()));
           table.addCell(String.valueOf(dba.get(i).getAuthor().getValue()));
           table.addCell(String.valueOf(dba.get(i).getPublisher_name().getValue()));
           table.addCell(String.valueOf(dba.get(i).getBook_publisher_year().getValue()));
           table.addCell(String.valueOf(dba.get(i).getBook_genre().getValue()));

       }
        subCatPart.add(table);
    }

    private  void addMetaData(Document document) {
        document.addTitle("All_Books");
        document.addSubject("Books in Library");
        document.addKeywords("Java, PDF");
        document.addAuthor("ZG4 Team");
        document.addCreator("ZG4 Team");
    }
    private  void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Books in Library", catFont);
        anchor.setName("Books in Library");
        Chapter catPart = new Chapter(new Paragraph(anchor),0);

        Paragraph subPara = new Paragraph();
        Section subCatPart = catPart.addSection(subPara);

        createTable(subCatPart);
        document.add(catPart);
    }
    public void populatePDF() {

        if (f.exists()){

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE+"AllBooks_Copy"+"("+counter+")"+".pdf"));
            document.open();
            addMetaData(document);
            addContent(document);
            document.close();
            f= new File(FILE+"AllBooks_Copy"+"("+counter+")"+".pdf");
            counter++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }else{
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(FILE+"AllBooks.pdf"));
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
