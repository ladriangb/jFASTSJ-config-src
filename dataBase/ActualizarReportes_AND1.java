package dataBase;

import java.util.ArrayList;
import java.util.Date;
import com.jswitch.base.modelo.Dominios;
import com.jswitch.base.modelo.HibernateUtil;
import com.jswitch.reporte.modelo.Reporte;
import com.jswitch.base.modelo.entidades.auditoria.AuditoriaBasica;
import com.jswitch.pagos.modelo.maestra.Factura;
import com.jswitch.persona.modelo.maestra.Persona;
import com.jswitch.persona.modelo.maestra.PersonaNatural;
import com.jswitch.siniestros.modelo.maestra.detalle.Reembolso;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 *
 * @author bc
 */
public class ActualizarReportes_AND1 {

    public ActualizarReportes_AND1() {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        System.out.println(s.createQuery("DELETE FROM " + Reporte.class.getName()).executeUpdate());

        AuditoriaBasica ab = new AuditoriaBasica(new Date(), "defaultdata", true);
        ArrayList<Reporte> list = new ArrayList<Reporte>(0);

        list.add(new Reporte(Dominios.CategoriaReporte.PERSONAS, 0, "PER-D001", "Personas x Nombre", "Todas las Personas", "FROM " + Persona.class.getName() + " as P ORDER BY nombreLargo", "Carta 8½ x 11 Vertical", false, true, true, false));
        list.add(new Reporte(Dominios.CategoriaReporte.PERSONAS, 0, "PER-D002", "Personas, Telefono, Direccion", "Todas las Personas con sus Telefonos y Direcciones", "FROM " + Persona.class.getName() + " as P ORDER BY nombreLargo", "Carta 8½ x 11 Vertical", false, true, true, false));
        list.add(new Reporte(Dominios.CategoriaReporte.PERSONAS, 0, "PER-D003", "Personas Naturales, Fecha Nacimiento, Sexo, Telefono y Direccion.", "Personas segun su Tipo, con Telefonos y Direccions", "FROM " + PersonaNatural.class.getName() + " as P ORDER BY nombreLargo", "Carta 8½ x 11 Vertical", false, true, true, false));
        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, 0, "PAGO_D_FACTURAS_001",
                "PAGOS PENDIENTES",
                "PAGOS PENDIENTE (FALTA FILTRO)",
                "FROM " + Factura.class.getName() + " as P "
                + "WHERE P.detalleSiniestro.etapaSiniestro.idPropio IN ('LIQ','ORD_PAG') "
                + "ORDER BY P.detalleSiniestro.personaPago.id, P.detalleSiniestro.siniestro.certificado.titular.tipoContrato.id ", "Carta 8½ x 11 Vertical",
                false, true, true, false));
       


        for (Reporte o : list) {
            s.saveOrUpdate(o);
        }


        tx.commit();
        s.close();
    }

    public static void main(String[] args) {
        new ActualizarReportes_AND1();
    }
}

/* 
        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, 0, "PAGO_D_FACTURAS_001", 
                "PAGOS PENDIENTES", 
                "DESCRIP", 
                "FROM " + Factura.class.getName() + " as P "
                + "WHERE P.detalleSiniestro.personaPago.id=18017 AND P.detalleSiniestro.etapaSiniestro.idPropio IN ('LIQ','ORD_PAG') "
                + "ORDER BY P.detalleSiniestro.personaPago.id, P.detalleSiniestro.siniestro.certificado.titular.tipoContrato.id ", "Carta 8½ x 11 Vertical", 
                false, true, true, false));
        
        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, 0, "LIQ_SINI_REM_D001", 
                "LIQUIDACION", 
                "Liquidacion de un Reembolso", 
                "FROM " + Reembolso.class.getName() + " as P "
                + "WHERE P.id=93739"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, 0, "LIQ_SINI_CLINICAS_D001", 
                "LIQUIDACION", 
                "Liquidacion de un siniestro", 
                "FROM " + DetalleSiniestro.class.getName() + " as P "
                + "WHERE P.id=93739"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));        

        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, 0, "SIN_NOTA_COBERTURA_D001", 
                "NOTA DE COBERTURA", 
                "Nota de cobertura de un siniestro", 
                "FROM " + DetalleSiniestro.class.getName() + " as P "
                + "WHERE P.id=93739"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));                  
        
        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SINI_APS_D001", 
                "ORDEN DE ATENCION MEDICA PRIMARIA", 
                "Orden de Atencion Médica Primaria", 
                "FROM " + APS.class.getName() + " as P "
                + "WHERE P.id=33018"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));       
        
        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN_CARTAAVAL_D001", 
                "CARTA AVAL", 
                "Carta Aval", 
                "FROM " + CartaAval.class.getName() + " as P "
                + "WHERE P.id=31566"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));         

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SINI_EMERGENCIA_D001", 
                "EMERGENCIA", 
                "Orden por Servicio de Emergencia", 
                "FROM " + Emergencia.class.getName() + " as P "
                + "WHERE P.id=33239"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));           
        
        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN_NOTASTECNICAS_D001", 
                "NOTAS TECNICAS", 
                "Notas tecnicas de un siniestro", 
                "FROM " + CartaAval.class.getName() + " as P "
                + "WHERE P.id=31566"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));         

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "ASE_SOL_REM_O_CARTAAVAL_D001", 
                "SOLICITUD DE REEMBOLSO Y/O CARTA AVAL", 
                "Solicitud de reembolso y/o Carta Aval", 
                "FROM "+Asegurado.class.getName()+" as P "
                + "WHERE P.id=26460"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));   
        
        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "ASE_PLANILLA_INCLUSION_D001", 
                "PLANILLA DE INCLUSION", 
                "Planilla de Inclusion", 
                "FROM "+Asegurado.class.getName()+" as P "
                + "WHERE P.id=26460"
                , "Carta 8½ x 11 Vertical", 
                false, true, true, false));          
        
        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R004", 
                "Listado de Ordenes de Pago por Remesa", 
                "Agrupados por Remesa, Nombre de Persona a Pagar", 
                "FROM " + Remesa.class.getName() + " as P "
                +"WHEER id=92926", 
                "Carta 8½ x 11 Vertical", 
                false, true, true, false));          
        
        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R001", 
                "Listado de Ordenes de Pago por Remesa", 
                "Agrupados por Remesa, Nombre de Persona a Pagar", 
                "FROM " + OrdenDePago.class.getName() + " as P "
                +" ORDER BY P.remesa.id, P.numeroOrden", 
                "Carta 8½ x 11 Vertical", 
                false, true, true, false));                
        
        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R003", 
                "Listado de Remesas", 
                "Remesas cargadas", 
                "FROM " + Remesa.class.getName() + " as P "
                +"ORDER BY P.fechaPago", 
                "Carta 8½ x 11 Horizontal", 
                false, true, true, false));
        
        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN-R001", "Listado de Diagnosticos Sinoiestros de Pago por Remesa", "Agrupados por Remesa, Nombre de Persona a Pagar", "SELECT P.diagnostico , P.montoPagado  FROM com.jswitch.siniestros.modelo.maestra.DiagnosticoSiniestro as P WHERE P.id=30921 ORDER BY P.diagnostico.nombre", "Carta 8½ x 11 Vertical", false, true, false, false));
//        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN-R001", "Listado de Diagnosticos Sinoiestros de Pago por Remesa", "Agrupados por Remesa, Nombre de Persona a Pagar", "FROM " + DiagnosticoSiniestro.class.getName() + " as P WHERE P.id=30921 ORDER BY P.diagnostico.nombre", "Carta 8½ x 11 Vertical", false, true, false));        

        list.add(new Reporte(Dominios.CategoriaReporte.x1, 0, "xxx3", "xx", "xx", "select id, montopagado, tratamientoescrito from sini_diagnosticosiniestro", "Carta 8½ x 11 Vertical", true, false, false, false));
        list.add(new Reporte(Dominios.CategoriaReporte.x1, 0, "x2", "x2", "x2", "", "Carta 8½ x 11 Vertical", false, true, false, false));
*/