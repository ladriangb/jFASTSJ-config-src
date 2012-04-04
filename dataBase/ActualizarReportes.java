package dataBase;

import com.jswitch.asegurados.modelo.maestra.Asegurado;
import com.jswitch.base.modelo.Dominios;
import com.jswitch.base.modelo.HibernateUtil;
import com.jswitch.base.modelo.entidades.auditoria.AuditoriaBasica;
import com.jswitch.pagos.modelo.maestra.Factura;
import com.jswitch.pagos.modelo.maestra.OrdenDePago;
import com.jswitch.pagos.modelo.maestra.Remesa;
import com.jswitch.persona.modelo.maestra.Persona;
import com.jswitch.persona.modelo.maestra.PersonaNatural;
import com.jswitch.reporte.modelo.Reporte;
import com.jswitch.siniestros.modelo.maestra.DetalleSiniestro;
import com.jswitch.siniestros.modelo.maestra.DiagnosticoSiniestro;
import com.jswitch.siniestros.modelo.maestra.detalle.APS;
import com.jswitch.siniestros.modelo.maestra.detalle.CartaAval;
import com.jswitch.siniestros.modelo.maestra.detalle.Emergencia;
import com.jswitch.siniestros.modelo.maestra.detalle.Reembolso;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 *
 * @author bc
 */
public class ActualizarReportes {

    public ActualizarReportes() {

        System.out.println("Act Reportes");
        Session s = HibernateUtil.getSessionFactory().openSession();
        System.out.println("sesion abierta");

        Transaction tx = s.beginTransaction();
        System.out.println("transaccion begin");

        System.out.println(s.createQuery("DELETE FROM " + Reporte.class.getName()).executeUpdate());

        System.out.println("viejos borrados ");

        AuditoriaBasica ab = new AuditoriaBasica(new Date(), "defaultdata", true);

        System.out.println("metodo reportes");

        ArrayList<Reporte> list = new ArrayList<Reporte>(0);

        //<editor-fold defaultstate="collapsed" desc="Personas">
        list.add(new Reporte(Dominios.CategoriaReporte.PERSONAS, Dominios.FiltroReporte.PERSONA ,0, "PER-D001",
                "Personas x Nombre",
                "Todas las Personas",
                "SELECT P.rif.rif AS rif, "
                + "P.nombreLargo AS nombreLargo "
                + "FROM "+Persona.class.getName()+" P "
                + "ORDER BY P.nombreLargo",
                "Carta 8½ x 11 Vertical",
                false, true, true, true));
        
        list.add(new Reporte(Dominios.CategoriaReporte.PERSONAS, Dominios.FiltroReporte.PERSONA ,0, "PER-D002",
                "Personas, Telefono, Direccion",
                "Todas las Personas con sus Telefonos y Direcciones",
                "FROM "+Persona.class.getName()+" P "
                + "ORDER BY P.nombreLargo",
                "Carta 8½ x 11 Vertical",
                false, true, true, false));
        
        list.add(new Reporte(Dominios.CategoriaReporte.PERSONAS, Dominios.FiltroReporte.PERSONA ,0, "PER-D003",
                "Personas Naturales, Fecha Nacimiento, Sexo, Telefono y Direccion.",
                "Personas segun su Tipo, con Telefonos y Direccions",
                "FROM " + PersonaNatural.class.getName() + " as P "
                + "ORDER BY nombreLargo",
                "Carta 8½ x 11 Vertical",
                false, true, true, false));
        
        list.add(new Reporte(Dominios.CategoriaReporte.PERSONAS, Dominios.FiltroReporte.PERSONA ,0, "PER-D004",
                "Personas x Tipo", "Todas las Personas",
                "SELECT P.nombreLargo AS nombreLargo, T.nombre AS nombre "
                + "FROM com.jswitch.persona.modelo.maestra.Persona AS P "
                + "LEFT JOIN P.tiposPersona AS T "
                + "ORDER BY T.nombre, P.nombreLargo",
                "Carta 8½ x 11 Vertical",
                false, true, true,true));
        
        list.add(new Reporte(Dominios.CategoriaReporte.PERSONAS, Dominios.FiltroReporte.PERSONA ,0, "PER-D005",
                "Personas x Banco",
                "Todas las Personas",
                "SELECT P.nombreLargo AS nombreLargo, B.banco.nombreLargo AS nombre "
                + "FROM com.jswitch.persona.modelo.maestra.Persona AS P LEFT JOIN P.cuentasBancarias AS B "
                + "ORDER BY B.banco.nombreLargo, P.nombreLargo",
                "Carta 8½ x 11 Vertical", false, true, true,true)
                );
        //</editor-fold>
        ///
        
        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, Dominios.FiltroReporte.FACTURA ,0, "PAGO_D_FACTURAS_001",
                "PAGOS PENDIENTES",
                "DESCRIP",
                "FROM " + Factura.class.getName() + " as P "
                + "WHERE P.detalleSiniestro.personaPago.id=18017 AND P.detalleSiniestro.etapaSiniestro.idPropio IN ('LIQ','ORD_PAG') "
                + "ORDER BY P.detalleSiniestro.personaPago.id, P.detalleSiniestro.siniestro.certificado.titular.tipoContrato.id ", 
                "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS,Dominios.FiltroReporte.FACTURA ,0, "PAGO_D_FACTURAS_001", 
                "PAGOS PENDIENTES", 
                "DESCRIP", 
                "FROM " + Factura.class.getName() + " as P "
                + "WHERE P.detalleSiniestro.personaPago.id=18017 AND P.detalleSiniestro.etapaSiniestro.idPropio IN ('LIQ','ORD_PAG') "
                + "ORDER BY P.detalleSiniestro.personaPago.id, P.detalleSiniestro.siniestro.certificado.titular.tipoContrato.id ", "Carta 8½ x 11 Vertical", 
                false, true, true, false));         
        
        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, Dominios.FiltroReporte.FACTURA ,0, "PAGO_D_FACTURAS_001", 
                "PAGOS PAGADOS", 
                "DESCRIP", 
                "FROM " + Factura.class.getName() + " as P "
                + "WHERE P.detalleSiniestro.etapaSiniestro.estatusSiniestro.nombre IN ('PAGADO') "
                + "ORDER BY P.detalleSiniestro.siniestro.certificado.titular.tipoContrato.nombre, P.detalleSiniestro.personaPago.id", "Carta 8½ x 11 Vertical", 
                false, true, true, false));       
        
        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, Dominios.FiltroReporte.REEMBOLSO ,0, "LIQ_SINI_REM_D001",
                "LIQUIDACION",
                "Liquidacion de un Reembolso",
                "FROM " + Reembolso.class.getName() + " as P "
                + "WHERE P.id=93739", "Carta 8½ x 11 Vertical",
                false, true, true, false));  

        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, 
                Dominios.FiltroReporte.DETALLESINIESTRO ,0, "LIQ_SINI_CLINICAS_D001",
                "LIQUIDACION",
                "Liquidacion de un siniestro",
                "FROM " + DetalleSiniestro.class.getName() + " as P "
                + "WHERE P.id=93739", "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.PAGOS, 0, "SIN_NOTA_COBERTURA_D001",
                "NOTA DE COBERTURA",
                "Nota de cobertura de un siniestro",
                "FROM " + DetalleSiniestro.class.getName() + " as P "
                + "WHERE P.id=93739", "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SINI_APS_D001",
                "ORDEN DE ATENCION MEDICA PRIMARIA",
                "Orden de Atencion Médica Primaria",
                "FROM " + APS.class.getName() + " as P "
                + "WHERE P.id=33018", "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN_CARTAAVAL_D001",
                "CARTA AVAL",
                "Carta Aval",
                "FROM " + CartaAval.class.getName() + " as P "
                + "WHERE P.id=31566", "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SINI_EMERGENCIA_D001",
                "EMERGENCIA",
                "Orden por Servicio de Emergencia",
                "FROM " + Emergencia.class.getName() + " as P "
                + "WHERE P.id=33239", "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN_NOTASTECNICAS_D001",
                "NOTAS TECNICAS",
                "Notas tecnicas de un siniestro",
                "FROM " + CartaAval.class.getName() + " as P "
                + "WHERE P.id=31566", "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "ASE_SOL_REM_O_CARTAAVAL_D001",
                "SOLICITUD DE REEMBOLSO Y/O CARTA AVAL",
                "Solicitud de reembolso y/o Carta Aval",
                "FROM " + Asegurado.class.getName() + " as P "
                + "WHERE P.id=26460", "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "ASE_PLANILLA_INCLUSION_D001",
                "PLANILLA DE INCLUSION",
                "Planilla de Inclusion",
                "FROM " + Asegurado.class.getName() + " as P "
                + "WHERE P.id=26460", "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R004",
                "Listado de Ordenes de Pago por Remesa",
                "Agrupados por Remesa, Nombre de Persona a Pagar",
                "FROM " + OrdenDePago.class.getName() + " as P "
                + "WHERE P.remesa.id=92926",
                "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R001",
                "REMESAS",
                "Listado de Remesas",
                "FROM " + Remesa.class.getName() + " as P "
                + " ORDER BY P.id, P.fechaPagado",
                "Carta 8½ x 11 Vertical",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R003",
                "Listado de Remesas",
                "Remesas cargadas",
                "FROM " + Remesa.class.getName() + " as P "
                + "ORDER BY P.fechaPago",
                "Carta 8½ x 11 Horizontal",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-D001",
                "FONDO AUTO-ADMINISTRADO DE SALUD",
                "Remesa",
                "FROM " + Remesa.class.getName() + " as P "
                + "WHERE P.id=94248",
                "Carta 8½ x 11 Horizontal",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R005",
                "REMESAS PAGADAS x TIPO DE CLIENTE A PAGAR",
                "Remesas Pagadas por Tipo de Cliente a Pagar",
                "SELECT P.tipoPersona.nombre AS tipoPersona,"
                + " count(P.tipoPersona.nombre) AS cantSiniestros,"
                + " sum(P.sumaDetalle.totalACancelar) AS total,"
                + " sum(case when P.siniestro.certificado.titular.persona.id = P.siniestro.asegurado.persona.id then P.sumaDetalle.totalACancelar else 0 end) AS montoTitular,"
                + " sum(case when P.siniestro.certificado.titular.persona.id = P.siniestro.asegurado.persona.id then 0.0 else P.sumaDetalle.totalACancelar end) AS montoBeneficiario,"
                + " sum(P.sumaDetalle.montoRetencionIslr) AS isrl "
                + " FROM "+DetalleSiniestro.class.getName()+" AS P"
                + " GROUP BY P.tipoPersona.nombre",
                "Carta 8½ x 11 Horizontal",
                false, true, true, true));
        
        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R006",
                "REMESAS PAGADAS x TIPO DE SINIESTRO",
                "Remesas Pagadas por Tipo por Tipo Siniestro",
                "SELECT P.tipoSiniestro.nombre AS tipoPersona,"
                + " count(P.tipoPersona.nombre) AS cantSiniestros,"
                + " sum(P.sumaDetalle.totalACancelar) AS total,"
                + " sum(case when P.siniestro.certificado.titular.persona.id = P.siniestro.asegurado.persona.id then P.sumaDetalle.totalACancelar else 0 end) AS montoTitular,"
                + " sum(case when P.siniestro.certificado.titular.persona.id = P.siniestro.asegurado.persona.id then 0.0 else P.sumaDetalle.totalACancelar end) AS montoBeneficiario,"
                + " sum(P.sumaDetalle.montoRetencionIslr) AS isrl "
                + " FROM "+DetalleSiniestro.class.getName()+" AS P"
                + " GROUP BY P.tipoSiniestro.nombre",
                "Carta 8½ x 11 Horizontal",
                false, true, true, true));      
        
        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, 0, "REM-R007",
                "REMESAS PAGADAS x TIPO TRAMITE",
                "Remesas Pagadas por Tipo de Tramite",
                "SELECT P.tipoDetalle AS tipoPersona,"
                + " count(P.tipoPersona.nombre) AS cantSiniestros,"
                + " sum(P.sumaDetalle.totalACancelar) AS total,"
                + " sum(case when P.siniestro.certificado.titular.persona.id = P.siniestro.asegurado.persona.id then P.sumaDetalle.totalACancelar else 0 end) AS montoTitular,"
                + " sum(case when P.siniestro.certificado.titular.persona.id = P.siniestro.asegurado.persona.id then 0.0 else P.sumaDetalle.totalACancelar end) AS montoBeneficiario,"
                + " sum(P.sumaDetalle.montoRetencionIslr) AS isrl "
                + " FROM "+DetalleSiniestro.class.getName()+" AS P"
                + " GROUP BY P.tipoDetalle",
                "Carta 8½ x 11 Horizontal",
                false, true, true, true));              

        list.add(new Reporte(Dominios.CategoriaReporte.ASEGURADOS, 0, "ASE_D0001",
                "ASEGURADOS",
                "Asegurados",
                "FROM " + Asegurado.class.getName() + " as P "
                + "ORDER BY P.certificado.id",
                "Carta 8½ x 11 Horizontal",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.ASEGURADOS, 0, "ASE_D0002",
                "ASEGURADOS",
                "Asegurados Agrupados por Tipo Contrato",
                "FROM " + Asegurado.class.getName() + " as P "
                + "ORDER BY P.certificado.titular.tipoContrato.nombre, P.certificado.titular.persona.rif.rif",
                "Carta 8½ x 11 Horizontal",
                false, true, true, false));

        list.add(new Reporte(Dominios.CategoriaReporte.ASEGURADOS, 0, "ASE_D0003",
                "ASEGURADOS",
                "Asegurados Agrupados por Certificado",
                "FROM " + Asegurado.class.getName() + " as P "
                + "ORDER BY P.certificado.titular.tipoContrato.nombre, P.certificado.titular.persona.nombreLargo",
                "Carta 8½ x 11 Horizontal",
                false, true, true, false));
        
        list.add(new Reporte(Dominios.CategoriaReporte.ASEGURADOS, 0, "ASE_D00033",
                "ASEGURADOS",
                "Asegurados Agrupados por Certificado",
                "SELECT P.persona AS persona, P.certificado AS certificado, P.parentesco AS parentesco "
                +"FROM " + Asegurado.class.getName() + " as P "
                + "ORDER BY P.certificado.titular.tipoContrato.nombre, P.certificado.titular.persona.nombreLargo",
                "Carta 8½ x 11 Horizontal",
                false, true, true, true));        

        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN-R001", 
                "Listado de Diagnosticos Siniestros de Pago por Remesa", 
                "Agrupados por Remesa, Nombre de Persona a Pagar", 
                "SELECT P.+ , P.montoPagado  "
                + "FROM "+DiagnosticoSiniestro.class.getName()+" as P "
                + "WHERE P.id=30921 ORDER BY P.diagnostico.nombre", 
                "Carta 8½ x 11 Vertical", 
                false, true, false, false));
        
        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN-R002", 
                "SINIESTROS", 
                "Listado de Siniestros",
                "SELECT P.presupuestadoInicial AS presupuestadoInicial, P.sumaDetalle AS sumaDetalle, P.siniestro AS siniestro "
                +"FROM "+DetalleSiniestro.class.getName()+" as P ", 
                "Carta 8½ x 11 Vertical", 
                false, true, true, true));        
        
        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN-R003", 
                "SINIESTROS", 
                "Listado de Siniestros Agrupados por Tipo de Contrato",
                "SELECT P.siniestro.certificado.titular.tipoContrato.nombre AS tipoContrato, "
                + "P.etapaSiniestro.estatusSiniestro.nombre AS estatusSiniestro, "
                + "SUM(P.presupuestadoInicial) AS presupuestadoInicial,"
                + "SUM(P.sumaDetalle.totalFacturado) AS totalFacturado, "
                + "SUM(P.sumaDetalle.totalLiquidado) AS totalLiquidado, "
                + "SUM(P.sumaDetalle.totalACancelar) AS totalACancelar, "
                + "SUM(P.sumaDetalle.montoNoAmparado) AS montoNoAmparado, "
                + "SUM(P.sumaDetalle.baseIslr) AS baseIslr, "
                + "SUM(P.sumaDetalle.montoRetencionIslr) AS montoRetencionIslr, "
                + "SUM(P.sumaDetalle.montoRetencionTM) AS montoRetencionTM, "
                + "COUNT(P.siniestro.certificado.titular.tipoContrato.nombre) AS cantSiniestros "
                + "FROM "+DetalleSiniestro.class.getName()+" AS P "
                + "WHERE P.etapaSiniestro.estatusSiniestro.nombre NOT IN('ANULADO')"
                + "GROUP BY P.siniestro.certificado.titular.tipoContrato.nombre, P.etapaSiniestro.estatusSiniestro.nombre "
                + "ORDER BY P.etapaSiniestro.estatusSiniestro.nombre", 
                "Carta 8½ x 11 Vertical", 
                false, true, true, true));                
        
//        list.add(new Reporte(Dominios.CategoriaReporte.SINIESTROS, 0, "SIN-R001", "Listado de Diagnosticos Sinoiestros de Pago por Remesa", "Agrupados por Remesa, Nombre de Persona a Pagar", "FROM " + DiagnosticoSiniestro.class.getName() + " as P WHERE P.id=30921 ORDER BY P.diagnostico.nombre", "Carta 8½ x 11 Vertical", false, true, false));        

        list.add(new Reporte(Dominios.CategoriaReporte.x1, 0, "xxx3", "xx", "xx", "select id, montopagado, tratamientoescrito from sini_diagnosticosiniestro", "Carta 8½ x 11 Vertical", true, false, false, false));
        list.add(new Reporte(Dominios.CategoriaReporte.x1, 0, "x2", "x2", "x2", "", "Carta 8½ x 11 Vertical", false, true, false, false));
        
        
        //los de nelson
        
        list.add(new Reporte(Dominios.CategoriaReporte.REMESAS, Dominios.FiltroReporte.ORDENPAGO ,0, "REM-COMISLR-D0033", 
                "COMPROBANTE ISLR", 
                "Comprobante de Retencion de Impuesto SObre la Renta",
                "SELECT P.personaPago.id AS id, "
                + "month(P.fechaPagado) AS mes, "
                + "year(P.fechaPagado) AS year, "
                + "sum(P.sumaOrden.totalACancelar) AS totalACancelar, "
                + "sum(P.sumaOrden.baseIslr) AS baseIslr, "
                + "sum(P.sumaOrden.montoRetencionIslr) AS montoRetencionIslr "
                + "FROM "+OrdenDePago.class.getName() +" P "
                + "WHERE P.personaPago.id=15143 AND P.fechaPagado IS NOT NULL "
                + "GROUP BY P.personaPago.id, month(P.fechaPagado), year(P.fechaPagado) "
                + "ORDER BY month(P.fechaPagado), year(P.fechaPagado)", 
                "Carta 8½ x 11 Vertical", 
                false, true, true, true)); 
        
        for (Reporte o : list) {
            s.saveOrUpdate(o);
        }

        System.out.println("antes del comit");

        tx.commit();
        System.out.println("comit");
        s.close();
        System.out.println("close");
        System.out.println("Listo");
    }

    public static void main(String[] args) {
        new ActualizarReportes();
    }
}
