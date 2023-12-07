package com.uabc.fiad.sgs.utils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.uabc.fiad.sgs.entity.Solicitud;
import com.uabc.fiad.sgs.entity.Usuario;
import com.uabc.fiad.sgs.service.IUsuarioService;

import jakarta.servlet.http.HttpServletResponse;

public class PDFExporter {
	
	private final Solicitud solicitud;
	private final Usuario usuario;
	
	public PDFExporter(Solicitud solicitud, Usuario usuario) {
		this.solicitud = solicitud;
		this.usuario = usuario;
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.LETTER, 80, 80, 50, 50);
		PdfWriter.getInstance(document, response.getOutputStream());
	
		document.open();

		BaseColor negro = BaseColor.BLACK;
        // Crear una fuente en negrita
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, negro);

        // Agregar título en negrita
        Paragraph title = new Paragraph("FACULTAD DE INGENIERÍA, ARQUITECTURA Y DISEÑO", boldFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(30f);
        document.add(title);
       
        // Agregar espacio
        document.add(new Paragraph(" "));

        // Agregar SUBDIRECCIÓN alineado a la derecha en negrita
        Font boldSubtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, negro);
        Paragraph subdireccion = new Paragraph("SUBDIRECCIÓN", boldSubtitleFont);
        subdireccion.setAlignment(Element.ALIGN_RIGHT);
        document.add(subdireccion);

        // Obtener la fecha actual en el formato deseado (día de la semana, día del mes de año)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "MX"));
        String fechaActual = dateFormat.format(new Date());

        Paragraph asunto = new Paragraph("ASUNTO: Oficio de comisión\nEnsenada, B.C. a " + fechaActual, boldSubtitleFont);
        asunto.setAlignment(Element.ALIGN_RIGHT);
        asunto.setSpacingAfter(40f);
        document.add(asunto);
        

        Font arialBold12 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, negro);
        Paragraph content = new Paragraph();
        content.setFont(arialBold12);
        content.add(usuario.getUsername()+ " "+usuario.getApPaterno()+" "+usuario.getApMaterno()+"\n# empleado "+usuario.getNumEmpleado()+"\nPRESENTE");
        content.setAlignment(Element.ALIGN_LEFT);
        content.setSpacingAfter(15f);
        document.add(content);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        Paragraph nuevoTexto = new Paragraph();
        nuevoTexto.setFont(FontFactory.getFont(FontFactory.HELVETICA, 12, negro));
        nuevoTexto.setFirstLineIndent(30);  // Sangría de la primera línea
        nuevoTexto.add("Por medio del presente la subdirección a mi cargo comisiona a usted el día "+solicitud.getFechaSalida().format(formatter)+" al "+solicitud.getFechaRegreso().format(formatter)+" del año en curso, a "+solicitud.getLugar()+"\n" +
                        "MOTIVO:  "+solicitud.getNombreEvento()+"\n" +
                        "\n" +
                        "Asimismo, se le solicita entregar a este Dependencia el reporte de actividades o la constancia respectiva de forma impresa o electrónica.\n" +
                        "\n" +
                        "En espera que reciba de conformidad, me despido de usted con un cordial saludo.");
        nuevoTexto.setSpacingAfter(30f);
        document.add(nuevoTexto);
        
        Font arialBold13 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, negro);
        Paragraph firmaSub = new Paragraph();
        firmaSub.setFont(arialBold13);  // Usar la fuente en negritas y Arial 13
        firmaSub.setAlignment(Element.ALIGN_CENTER);  // Centrar el párrafo
        firmaSub.setSpacingAfter(10f);  // Espacio después del párrafo
        firmaSub.add("ATENTAMENTE\n\"POR LA REALIZACIÓN PLENA DEL SER\"\n\n\n\n\n\n DR. HUMBERTO CERVANTES DE AVILA");
        firmaSub.setSpacingAfter(20f);
        document.add(firmaSub);
        Font arial12 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, negro);

        String espacios = "                                                                                              "; // Ajusta la cantidad de espacios según tus necesidades

        // Texto que quieres mostrar con espacios/tabulaciones antes del texto
        String texto = espacios + "Académico\n\n";
        // Agregar el texto al documento
        Paragraph paragraph = new Paragraph(texto, arial12);
        document.add(paragraph);
        
        // Crear fuente en Arial 10
        Font arial10 = FontFactory.getFont(FontFactory.HELVETICA, 10, negro);

        Paragraph firmaRecibido = new Paragraph();
        firmaRecibido.setAlignment(Element.ALIGN_RIGHT);
        firmaRecibido.setFont(arial10); 
        firmaRecibido.add("Fecha y firma de Recibido: ____________________________");
        document.add(firmaRecibido);
        
        Paragraph ua = new Paragraph();
        ua.setAlignment(Element.ALIGN_RIGHT);
        ua.setFont(arial10); 
        ua.add("Otra(s) UA de Adscripción: ____________________________");
        ua.setSpacingAfter(40f);
        document.add(ua);
        
        
        Paragraph footer = new Paragraph();
        footer.setAlignment(Element.ALIGN_LEFT);
        footer.setFont(arial10); 
        footer.add("c. c. p. Jessica Lagos Fregoso jefe del Depto. de recursos humanos ensenada\n"
        		+ "c. c. p. expediente\n"
        		+ "HCDA-eliud");
        document.add(footer);
        
        
        
		document.close();
	}

}
