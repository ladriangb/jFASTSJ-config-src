package dataBase;

import com.jswitch.base.modelo.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * Crea las vistas en la base de datos
 * @author Luis Adrian Gonzalez Benavides 
 * @author bc
 */
public class ActualizarVistas {

    /**
     * Crea las vistas en la base de datos
     */
    public ActualizarVistas() {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        System.out.println("DROP TABLES");
        try {
            System.out.println("view_sumafactura:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_sumafactura").executeUpdate();
        } catch (Exception ex) {
            tx.rollback();
            tx = s.beginTransaction();
        }
        try {
            System.out.println("view_sumadetalle:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_sumadetalle").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        tx = s.beginTransaction();
        try {
            System.out.println("view_sumaorden:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_sumaorden").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        tx = s.beginTransaction();
        try {
            System.out.println("view_sumaremesa:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_sumaremesa").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        tx = s.beginTransaction();
        try {
            System.out.println("view_sumadesglosecobertura:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_sumadesglosecobertura").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        tx = s.beginTransaction();
        try {
            System.out.println("view_sumapartida:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_sumapartida").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        tx = s.beginTransaction();
        try {
            System.out.println("view_sumapartidaremesa:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_sumapartidaremesa").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        tx = s.beginTransaction();
        try {
            System.out.println("view_listadiagnostico:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_listadiagnostico").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        tx = s.beginTransaction();
        try {
            System.out.println("view_agotamiento:");
            s.createSQLQuery("DROP TABLE IF EXISTS view_agotamiento").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        }
        tx = s.beginTransaction();

        //<editor-fold defaultstate="collapsed" desc="suma desglose cobertura">
        String sumaDesgloseCobertura = " DROP VIEW IF EXISTS view_sumadesglosecobertura CASCADE;"
                + " CREATE OR REPLACE VIEW view_sumadesglosecobertura AS "
                + "	SELECT "
                + "     (sini_detallesiniestro.id ||'-'|| sini_desglosecobertura.cobertura_id) id,"
                + "     sini_detallesiniestro.id detallesiniestro_id, "
                + "	ROUND(CAST(SUM(sini_desglosecobertura.montoamparado) AS NUMERIC),2) AS montoamparado, "
                + "	ROUND(CAST(SUM(sini_desglosecobertura.montofacturado) AS NUMERIC),2) AS montofacturado, "
                + "	ROUND(CAST(SUM(sini_desglosecobertura.montonoamparado) AS NUMERIC),2) AS montonoamparado, "
                + "	sini_desglosecobertura.cobertura_id "
                + "	FROM sini_desglosecobertura "
                + "	CROSS JOIN sini_factura "
                + "	CROSS JOIN sini_detallesiniestro "
                + "	WHERE sini_desglosecobertura.factura_id = sini_factura.id AND sini_factura.detallesiniestro_id = sini_detallesiniestro.id "
                + "	GROUP BY sini_desglosecobertura.cobertura_id, sini_detallesiniestro.id;"
                + "	ALTER TABLE view_sumadesglosecobertura OWNER TO postgres;";
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="agotamiento">
        String agotamiento = " DROP VIEW IF EXISTS view_agotamiento CASCADE;"
                + " CREATE OR REPLACE VIEW view_agotamiento AS "
                + "SELECT (siniestro2_.asegurado_id||'-'||diagnostic0_.diagnostico_id||'-'|| siniestro2_.ayo) AS id, "
                + "siniestro2_.asegurado_id, diagnostic0_.diagnostico_id,  "
                + "ROUND(CAST(SUM( "
                + "	CASE "
                + "	WHEN upper(estatussin7_.nombre) = 'PAGADO' THEN diagnostic0_.montopagado "
                + "	ELSE 0 "
                + "END) AS NUMERIC),2) AS montopagado, "
                + "ROUND(CAST(SUM( "
                + "	CASE "
                + "	WHEN upper(estatussin7_.nombre) = 'PENDIENTE' THEN diagnostic0_.montopendiente + diagnostic0_.montopagado "
                + "	ELSE 0 "
                + "END) AS NUMERIC),2) AS montopendiente, siniestro2_.ayo "
                + "FROM sini_diagnosticosiniestro diagnostic0_, sini_detallesiniestro detallesin1_, sini_siniestro siniestro2_, sini_etapasiniestro etapasinie6_, sini_estatussiniestro estatussin7_ "
                + "WHERE diagnostic0_.detallesiniestro_id = detallesin1_.id AND detallesin1_.siniestro_id = siniestro2_.id AND detallesin1_.etapasiniestro_id = etapasinie6_.id AND etapasinie6_.estatussiniestro_id = estatussin7_.id GROUP BY siniestro2_.asegurado_id, siniestro2_.ayo, diagnostic0_.diagnostico_id; "
                + "ALTER TABLE view_agotamiento OWNER TO postgres;";
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="suma factura">
        String sumaFactura = " DROP VIEW IF EXISTS view_sumafactura CASCADE;"
                + " CREATE OR REPLACE VIEW view_sumafactura AS  "
                + "SELECT sini_factura.id, "
                + "ROUND(CAST(SUM(sini_factura.totalliquidado * sini_factura.porcentajeretencionprontopago) AS NUMERIC),2) AS montoretencionprontopago,  "
                + "ROUND(CAST(SUM(sini_factura.totalliquidado * sini_factura.porcentajeretenciontm) AS NUMERIC),2) AS montoretenciontm, "
                + "ROUND(CAST(SUM(	 "
                + "(sini_factura.montoretenciondeducible + sini_factura.montoretencioniva + sini_factura.montoretencionislr)  "
                + "+ (sini_factura.totalliquidado * sini_factura.porcentajeretenciontm) "
                + "+ (sini_factura.totalliquidado * sini_factura.porcentajeretencionprontopago) "
                + ") AS NUMERIC),2) AS totalretenido,  "
                + "ROUND(CAST(SUM(	 "
                + "sini_factura.totalliquidado -  "
                + "((sini_factura.montoretenciondeducible + sini_factura.montoretencioniva + sini_factura.montoretencionislr)  "
                + "+ (sini_factura.totalliquidado * sini_factura.porcentajeretenciontm) "
                + "+ (sini_factura.totalliquidado * sini_factura.porcentajeretencionprontopago)) "
                + ") AS NUMERIC),2) AS totalacancelar "
                + "FROM sini_factura "
                + "WHERE sini_factura.activo = true "
                + "GROUP BY sini_factura.id; "
                + "ALTER TABLE view_sumafactura OWNER TO postgres; ";
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="suma Detalle">
        String sumaDetalle = " CREATE OR REPLACE VIEW view_sumadetalle AS  "
                + "SELECT sini_factura.detallesiniestro_id AS id, "
                + "COUNT(sini_factura.id) AS cantidadfacturas, "
                + "ROUND(CAST(SUM(sini_factura.baseislr) AS NUMERIC),2) AS baseislr,  "
                + "ROUND(CAST(SUM(sini_factura.baseiva) AS NUMERIC),2) AS baseiva, "
                + "ROUND(CAST(SUM(sini_factura.gastosclinicos) AS NUMERIC),2) AS gastosclinicos, "
                + "ROUND(CAST(SUM(sini_factura.honorariosmedicos) AS NUMERIC),2) AS honorariosmedicos, "
                + "ROUND(CAST(SUM(sini_factura.montoamparado) AS NUMERIC),2) AS montoamparado, "
                + "ROUND(CAST(SUM(sini_factura.montoretenciondeducible) AS NUMERIC),2) AS montoretenciondeducible, "
                + "ROUND(CAST(SUM(view_sumafactura.montoretencionprontopago) AS NUMERIC),2) AS montoretencionprontopago, "
                + "ROUND(CAST(SUM(sini_factura.montoiva) AS NUMERIC),2) AS montoiva, "
                + "ROUND(CAST(SUM(sini_factura.montonoamparado) AS NUMERIC),2) AS montonoamparado, "
                + "ROUND(CAST(SUM(sini_factura.montoretencionislr) AS NUMERIC),2) AS montoretencionislr, "
                + "ROUND(CAST(SUM(sini_factura.montoretencioniva) AS NUMERIC),2) AS montoretencioniva, "
                + "ROUND(CAST(SUM(view_sumafactura.montoretenciontm) AS NUMERIC),2) AS montoretenciontm, "
                + "ROUND(CAST(SUM(view_sumafactura.totalacancelar) AS NUMERIC),2) AS totalacancelar, "
                + "ROUND(CAST(SUM(sini_factura.totalfacturado) AS NUMERIC),2) AS totalfacturado, "
                + "ROUND(CAST(SUM(sini_factura.totalliquidado) AS NUMERIC),2) AS totalliquidado, "
                + "ROUND(CAST(SUM(view_sumafactura.totalretenido) AS NUMERIC),2) AS totalretenido "
                + "FROM sini_factura "
                + "CROSS JOIN view_sumafactura "
                + "WHERE sini_factura.id = view_sumafactura.id "
                + "GROUP BY sini_factura.detallesiniestro_id; "
                + "ALTER TABLE view_sumadetalle OWNER TO postgres; ";
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Suma Orden">
        String sumaOrden = " CREATE OR REPLACE VIEW view_sumaorden AS  "
                + "	SELECT detallesin1_.ordendepago_id AS id, count(sumadetall0_.id) AS cantidaddetalles,  "
                + "	ROUND(CAST(SUM( "
                + "	CASE "
                + "	WHEN titular5_.persona_id = asegurado8_.persona_id THEN 1 "
                + "	ELSE 0 "
                + "	END) AS NUMERIC),2) AS numerosiniestrostitular,  "
                + "	ROUND(CAST(SUM( "
                + "	CASE "
                + "	WHEN titular5_.persona_id <> asegurado8_.persona_id THEN 1 "
                + "	ELSE 0 "
                + "	END) AS NUMERIC),2) AS numerosiniestrosfamiliar,  "
                + "	ROUND(CAST(SUM( "
                + "	CASE "
                + "	WHEN titular5_.persona_id = asegurado8_.persona_id THEN sumadetall0_.totalliquidado "
                + "	ELSE 0 "
                + "	END) AS NUMERIC),2) AS montotitulares,  "
                + "	ROUND(CAST(SUM( "
                + "	CASE "
                + "	WHEN titular5_.persona_id <> asegurado8_.persona_id THEN sumadetall0_.totalliquidado "
                + "	ELSE 0 "
                + "	END) AS NUMERIC),2) AS montofamiliares,  "
                + "	ROUND(CAST(SUM(sumadetall0_.baseislr) AS NUMERIC),2) AS baseislr,  "
                + "	ROUND(CAST(SUM(sumadetall0_.baseiva) AS NUMERIC),2) AS baseiva,  "
                + "	ROUND(CAST(SUM(sumadetall0_.cantidadfacturas) AS NUMERIC),2) AS cantidadfacturas,  "
                + "	ROUND(CAST(SUM(sumadetall0_.gastosclinicos) AS NUMERIC),2) AS gastosclinicos,  "
                + "	ROUND(CAST(SUM(sumadetall0_.honorariosmedicos) AS NUMERIC),2) AS honorariosmedicos,  "
                + "	ROUND(CAST(SUM(sumadetall0_.montoamparado) AS NUMERIC),2) AS montoamparado,  "
                + "	ROUND(CAST(SUM(sumadetall0_.montoretenciondeducible) AS NUMERIC),2) AS montoretenciondeducible,  "
                + "	ROUND(CAST(SUM(sumadetall0_.montoretencionprontopago) AS NUMERIC),2) AS montoretencionprontopago,  "
                + "	ROUND(CAST(SUM(sumadetall0_.montoiva) AS NUMERIC),2) AS montoiva,  "
                + "	ROUND(CAST(SUM(sumadetall0_.montonoamparado) AS NUMERIC),2) AS montonoamparado,  "
                + "	ROUND(CAST(SUM(sumadetall0_.montoretencionislr) AS NUMERIC),2) AS montoretencionislr,  "
                + "	ROUND(CAST(SUM(sumadetall0_.montoretencioniva) AS NUMERIC),2) AS montoretencioniva,  "
                + "	ROUND(CAST(SUM(sumadetall0_.montoretenciontm) AS NUMERIC),2) AS montoretenciontm,  "
                + "	ROUND(CAST(SUM(sumadetall0_.totalacancelar) AS NUMERIC),2) AS totalacancelar,  "
                + "	ROUND(CAST(SUM(sumadetall0_.totalfacturado) AS NUMERIC),2) AS totalfacturado,  "
                + "	ROUND(CAST(SUM(sumadetall0_.totalliquidado) AS NUMERIC),2) AS totalliquidado,  "
                + "	ROUND(CAST(SUM(sumadetall0_.totalretenido) AS NUMERIC),2) AS totalretenido "
                + "	FROM view_sumadetalle sumadetall0_ "
                + "	CROSS JOIN sini_detallesiniestro detallesin1_ "
                + "	CROSS JOIN sini_siniestro siniestro3_ "
                + "	CROSS JOIN aseg_certificado certificad4_ "
                + "	CROSS JOIN aseg_titular titular5_ "
                + "	CROSS JOIN aseg_asegurado asegurado8_ "
                + "	WHERE sumadetall0_.id = detallesin1_.id AND detallesin1_.siniestro_id = siniestro3_.id AND siniestro3_.certificado_id = certificad4_.id AND certificad4_.titular_id = titular5_.id AND siniestro3_.asegurado_id = asegurado8_.id AND detallesin1_.ordendepago_id IS NOT NULL "
                + "	GROUP BY detallesin1_.ordendepago_id; "
                + "	ALTER TABLE view_sumaorden OWNER TO postgres; ";
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Suma Remesa">
        String sumaRemesa = " CREATE OR REPLACE VIEW view_sumaremesa AS "
                + " SELECT pago_ordendepago.remesa_id AS id,"
                + " COUNT(view_sumaorden.id) AS cantidadordenes,"
                + " ROUND(CAST(SUM(view_sumaorden.cantidaddetalles) AS NUMERIC),2) AS cantidaddetalles,"
                + " ROUND(CAST(SUM(view_sumaorden.numerosiniestrostitular) AS NUMERIC),2) AS numerosiniestrostitular,"
                + " ROUND(CAST(SUM(view_sumaorden.numerosiniestrosfamiliar) AS NUMERIC),2) AS numerosiniestrosfamiliar,"
                + " ROUND(CAST(SUM(view_sumaorden.montotitulares) AS NUMERIC),2) AS montotitulares,"
                + " ROUND(CAST(SUM(view_sumaorden.montofamiliares) AS NUMERIC),2) AS montofamiliares,"
                + " ROUND(CAST(SUM(view_sumaorden.baseislr) AS NUMERIC),2) AS baseislr,"
                + " ROUND(CAST(SUM(view_sumaorden.baseiva) AS NUMERIC),2) AS baseiva,"
                + " ROUND(CAST(SUM(view_sumaorden.cantidadfacturas) AS NUMERIC),2) AS cantidadfacturas,"
                + " ROUND(CAST(SUM(view_sumaorden.gastosclinicos) AS NUMERIC),2) AS gastosclinicos,"
                + " ROUND(CAST(SUM(view_sumaorden.honorariosmedicos) AS NUMERIC),2) AS honorariosmedicos,"
                + " ROUND(CAST(SUM(view_sumaorden.montoamparado) AS NUMERIC),2) AS montoamparado,"
                + " ROUND(CAST(SUM(view_sumaorden.montoretenciondeducible) AS NUMERIC),2) AS montoretenciondeducible,"
                + " ROUND(CAST(SUM(view_sumaorden.montoretencionprontopago) AS NUMERIC),2) AS montoretencionprontopago,"
                + " ROUND(CAST(SUM(view_sumaorden.montoiva) AS NUMERIC),2) AS montoiva,"
                + " ROUND(CAST(SUM(view_sumaorden.montonoamparado) AS NUMERIC),2) AS montonoamparado,"
                + " ROUND(CAST(SUM(view_sumaorden.montoretencionislr) AS NUMERIC),2) AS montoretencionislr,"
                + " ROUND(CAST(SUM(view_sumaorden.montoretencioniva) AS NUMERIC),2) AS montoretencioniva,"
                + " ROUND(CAST(SUM(view_sumaorden.montoretenciontm) AS NUMERIC),2) AS montoretenciontm,"
                + " ROUND(CAST(SUM(view_sumaorden.totalacancelar) AS NUMERIC),2) AS totalacancelar,"
                + " ROUND(CAST(SUM(view_sumaorden.totalfacturado) AS NUMERIC),2) AS totalfacturado,"
                + " ROUND(CAST(SUM(view_sumaorden.totalliquidado) AS NUMERIC),2) AS totalliquidado,"
                + " ROUND(CAST(SUM(view_sumaorden.totalretenido) AS NUMERIC),2) AS totalretenido"
                + " FROM view_sumaorden"
                + " CROSS JOIN pago_ordendepago"
                + " WHERE view_sumaorden.id = pago_ordendepago.id AND  pago_ordendepago.remesa_id IS NOT NULL"
                + " GROUP BY pago_ordendepago.remesa_id;"
                + " ALTER TABLE view_sumaremesa OWNER TO postgres;";
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Lista Diagnostico">
        String listaDiagnostico = " DROP VIEW IF EXISTS view_listadiagnostico CASCADE;"
                + "CREATE OR REPLACE VIEW view_listadiagnostico AS "
                + " SELECT DISTINCT (((siniestro0_.id || '-') || "
                + " CASE"
                + " WHEN detallesin1_.id IS NOT NULL THEN detallesin1_.id"
                + " ELSE (-1)"
                + " END) || '-') || "
                + " CASE"
                + " WHEN diag.diagnostico_id IS NOT NULL THEN diag.diagnostico_id"
                + " ELSE (-1)"
                + " END AS id, siniestro0_.id AS siniestro_id, detallesin1_.id AS detallesiniestro_id, diag.diagnostico_id"
                + " FROM sini_siniestro siniestro0_"
                + " LEFT JOIN sini_detallesiniestro detallesin1_ ON siniestro0_.id = detallesin1_.siniestro_id"
                + " LEFT JOIN sini_diagnosticosiniestro diag ON detallesin1_.id = diag.detallesiniestro_id"
                + " ORDER BY siniestro0_.id;"
                + " ALTER TABLE view_listadiagnostico OWNER TO postgres;";
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Suma Partida">
        String sumaPartida = " DROP VIEW IF EXISTS view_sumapartida CASCADE;"
                + "CREATE OR REPLACE VIEW view_sumapartida AS "
                + " SELECT (detallesin0_.ordendepago_id||'-'||tipocontra4_.partidapresupuestaria_id) AS id,"
                + " tipocontra4_.partidapresupuestaria_id partidapresupuestaria_id,"
                + " detallesin0_.ordendepago_id ordendepago_id,"
                + " count(suma.id) AS cantidaddetalles,"
                + " ROUND(CAST(SUM(suma.cantidadfacturas) AS NUMERIC),2) AS cantidadfacturas,"
                + " ROUND(CAST(SUM(suma.baseislr) AS NUMERIC),2) AS baseislr, ROUND(CAST(SUM(suma.baseiva) AS NUMERIC),2) AS baseiva,"
                + " ROUND(CAST(SUM(suma.gastosclinicos) AS NUMERIC),2) AS gastosclinicos, ROUND(CAST(SUM(suma.honorariosmedicos) AS NUMERIC),2) AS honorariosmedicos,"
                + " ROUND(CAST(SUM(suma.montoamparado) AS NUMERIC),2) AS montoamparado, ROUND(CAST(SUM(suma.montoretenciondeducible) AS NUMERIC),2) AS montoretenciondeducible,"
                + " ROUND(CAST(SUM(suma.montoretencionprontopago) AS NUMERIC),2) AS montoretencionprontopago, ROUND(CAST(SUM(suma.montoiva) AS NUMERIC),2) AS montoiva,"
                + " ROUND(CAST(SUM(suma.montonoamparado) AS NUMERIC),2) AS montonoamparado, ROUND(CAST(SUM(suma.montoretencionislr) AS NUMERIC),2) AS montoretencionislr,"
                + " ROUND(CAST(SUM(suma.montoretencioniva) AS NUMERIC),2) AS montoretencioniva, ROUND(CAST(SUM(suma.montoretenciontm) AS NUMERIC),2) AS montoretenciontm,"
                + " ROUND(CAST(SUM(suma.totalacancelar) AS NUMERIC),2) AS totalacancelar, ROUND(CAST(SUM(suma.totalfacturado) AS NUMERIC),2) AS totalfacturado,"
                + " ROUND(CAST(SUM(suma.totalliquidado) AS NUMERIC),2) AS totalliquidado, ROUND(CAST(SUM(suma.totalretenido) AS NUMERIC),2) AS totalretenido"
                + " FROM sini_detallesiniestro detallesin0_"
                + " CROSS JOIN view_sumadetalle suma"
                + " CROSS JOIN sini_siniestro siniestro1_"
                + " CROSS JOIN aseg_certificado certificad2_"
                + " CROSS JOIN aseg_titular titular3_"
                + " CROSS JOIN aseg_tipocontrato tipocontra4_"
                + " WHERE detallesin0_.siniestro_id = siniestro1_.id AND suma.id = detallesin0_.id AND siniestro1_.certificado_id = certificad2_.id AND certificad2_.titular_id = titular3_.id AND titular3_.tipocontrato_id = tipocontra4_.id"
                + " GROUP BY tipocontra4_.partidapresupuestaria_id, detallesin0_.ordendepago_id;"
                + " ALTER TABLE view_sumapartida OWNER TO postgres;";
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="sumaPartidaRemesa">
        String sumaPartidaRemesa = " CREATE OR REPLACE VIEW view_sumapartidaremesa AS "
                + "  SELECT (pago_ordendepago.remesa_id || '-' || view_sumapartida.partidapresupuestaria_id) AS id, pago_ordendepago.remesa_id, view_sumapartida.partidapresupuestaria_id, sum(view_sumapartida.cantidaddetalles) AS cantidaddetalles, sum(view_sumapartida.cantidadfacturas) AS cantidadfacturas, sum(view_sumapartida.baseislr) AS baseislr, sum(view_sumapartida.baseiva) AS baseiva, sum(view_sumapartida.gastosclinicos) AS gastosclinicos, sum(view_sumapartida.honorariosmedicos) AS honorariosmedicos, sum(view_sumapartida.montoamparado) AS montoamparado, sum(view_sumapartida.montoretenciondeducible) AS montoretenciondeducible, sum(view_sumapartida.montoretencionprontopago) AS montoretencionprontopago, sum(view_sumapartida.montoiva) AS montoiva, sum(view_sumapartida.montonoamparado) AS montonoamparado, sum(view_sumapartida.montoretencionislr) AS montoretencionislr, sum(view_sumapartida.montoretencioniva) AS montoretencioniva, sum(view_sumapartida.montoretenciontm) AS montoretenciontm, sum(view_sumapartida.totalacancelar) AS totalacancelar, sum(view_sumapartida.totalfacturado) AS totalfacturado, sum(view_sumapartida.totalliquidado) AS totalliquidado, sum(view_sumapartida.totalretenido) AS totalretenido"
                + "    FROM view_sumapartida, pago_ordendepago"
                + "   WHERE pago_ordendepago.id = view_sumapartida.ordendepago_id"
                + "   GROUP BY pago_ordendepago.remesa_id, view_sumapartida.partidapresupuestaria_id;"
                + " ALTER TABLE view_sumapartidaremesa OWNER TO postgres;";
        //</editor-fold>
        s.createSQLQuery(sumaDesgloseCobertura
                + agotamiento
                + sumaFactura
                + sumaDetalle
                + sumaOrden
                + sumaRemesa
                + listaDiagnostico
                + sumaPartida
                + sumaPartidaRemesa).executeUpdate();

        tx.commit();
        System.out.println("Vistas Creadas");
        s.close();
        System.out.println("Listo");
    }

    public static void main(String[] args) {
        new ActualizarVistas();
    }
}
