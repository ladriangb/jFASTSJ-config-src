package dataBase;

import com.jswitch.base.modelo.HibernateUtil;
import com.jswitch.siniestros.modelo.maestra.DetalleSiniestro;
import com.jswitch.siniestros.modelo.maestra.DiagnosticoSiniestro;
import com.jswitch.siniestros.modelo.maestra.Siniestro;
import com.jswitch.vistasbd.SumaPartidaRemesa;
import java.util.Date;
import java.util.List;
import org.hibernate.classic.Session;

/**
 *
 * @author bc
 */
public class ActualizarParametros_1 {

    public ActualizarParametros_1() {
        System.out.println("Act Parametros");
        Session s = HibernateUtil.getSessionFactory().openSession();
        System.out.println("sesion abierta");
        System.out.println(
                s.createQuery("FROM " + SumaPartidaRemesa.class.getName()).list());

        //Transaction tx = s.beginTransaction();
        System.out.println("transaccion begin");



        System.out.println("---------*----------------");

      
        s.beginTransaction();
        int i = s.createQuery("SELECT C.monto as D FROM "
                        + "                  RangoValor.class.getName()"
                        + "                  C WHERE C.prontoPago.persona.id =:pp "
                        + "                  AND (date(:fp)-f.fechaFacturado) "
                        + "                  BETWEEN C.minValue AND C.maxValue").
                setDate(0, new Date()).executeUpdate();

        System.out.println(i);
        s.getTransaction().commit();
//           Response res = HibernateUtils.getAllFromQuery(
//                    filteredColumns,
//                    currentSortedColumns,
//                    currentSortedVersusColumns,
//                    valueObjectType,
//                    sql,
//                    new Object[0],
//                    new Type[0],
//                    "C",
//                    sf,
//                    s);


//        Diagnostico d = (Diagnostico) s.createQuery("FROM " + Diagnostico.class.getName()).setMaxResults(1).uniqueResult();
//        Asegurado a = (Asegurado) s.createQuery("FROM " + Asegurado.class.getName()).setMaxResults(1).uniqueResult();
//
//        System.out.println(d.getEspecialidad().getRamo().getNombre());
//        System.out.println(d.getEspecialidad().getNombre());
//        System.out.println(d.getNombre());

//        Double totalCoberturaDiagnosticoPlan = (Double) s.createQuery("SELECT sumaAmparada.monto FROM "
//                + SumaAsegurada.class.getName() + " WHERE diagnostico.id=:diag AND "
//                + "plan.id=:plan").setLong("diag", d.getId()).setLong("plan", a.getPlan().getId()).uniqueResult();
//
//        System.out.println(totalCoberturaDiagnosticoPlan);




//        s.createQuery("SELECT D.detalleSiniestro.siniestro.ayo, D.detalleSiniestro.siniestro.asegurado.id, "
//                + "D.diagnostico.id, SUM(D.montoPagado) "
//                + " FROM "
//                + DiagnosticoSiniestro.class.getName() + " as D WHERE "
//                //+ "detalleSiniestro.siniestro.asegurado.id=:aseg AND "
//                //+ "diagnostico.id=:diag AND "
//                + " UPPER(D.detalleSiniestro.etapaSiniestro.estatusSiniestro.nombre)=:estatus "
//                + " GROUP BY D.detalleSiniestro.siniestro.asegurado.id,"
//                + " D.detalleSiniestro.siniestro.ayo, D.diagnostico.id") //.setLong("aseg", a.getId())
//                //.setLong("diag", d.getId())
//                .setString("estatus", "PAGADO").list();

        //System.out.println(pagado);
//        Double pendiente = (Double) s.createQuery("SELECT SUM(montoPagado+montoPendiente) FROM "
//                + DiagnosticoSiniestro.class.getName() + " WHERE "
//                + "detalleSiniestro.siniestro.asegurado.id=:aseg AND "
//                + "diagnostico.id=:diag AND "
//                + "UPPER(detalleSiniestro.etapaSiniestro.estatusSiniestro.nombre)=:estatus")
//                .setLong("aseg", a.getId())
//                .setLong("diag", d.getId())
//                .setString("estatus", "PENDIENTE")
//                .uniqueResult();
//
//        System.out.println(pendiente);

        System.out.println("-----------***---------------------");
        System.out.println("comit");
        s.close();
        System.out.println("close");
        System.out.println("Listo");
    }

    public static void main(String[] args) {
        new ActualizarParametros_1();
    }
}
