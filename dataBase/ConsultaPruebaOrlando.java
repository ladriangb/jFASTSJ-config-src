package dataBase;

import com.jswitch.base.modelo.HibernateUtil;

import com.jswitch.configuracion.modelo.transaccional.RangoValor;
import com.jswitch.pagos.modelo.maestra.Factura;
import com.jswitch.persona.modelo.maestra.PersonaNatural;
import com.jswitch.vistasbd.vista1;
import java.util.Date;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 *
 * @author bc
 */
public class ConsultaPruebaOrlando {

    public ConsultaPruebaOrlando() {
        System.out.println("Act Parametros_2");
        Session s = HibernateUtil.getSessionFactory().openSession();
        System.out.println("sesion abierta");

        Transaction tx = s.beginTransaction();
        System.out.println("transaccion begin");
        Double d65 = (Double) s.createQuery("update "
                + Factura.class.getName()
                + " C WHERE C.prontoPago.id =:pp "
                + "AND (date(:fechaPagado)-date(:fechaFacturado)) "
                + "BETWEEN C.minValue AND C.maxValue").
                setLong("pp", 97513l).
                setDate("fechaPagado", new Date()).
                setDate("fechaFacturado", new Date(99, 07, 07)).
                setMaxResults(1).uniqueResult();
        System.out.println(d65);
        
        s.createQuery("SELECT coalesce(C.monto,0) as D FROM "
                + RangoValor.class.getName()
                + " C WHERE C.prontoPago.id =:pp "
                + "AND (date(:fechaPagado)-date(:fechaFacturado)) "
                + "BETWEEN C.minValue AND C.maxValue)").
                setLong("pp", 9753l).
                setDate("fechaPagado", new Date()).
                setDate("fechaFacturado", new Date(99, 07, 07)).
                setMaxResults(1).uniqueResult();
        
        Date d = new Date();
        Date d2 = new Date(88, 07, 18);
        System.out.println(
                s.createQuery("SELEct (day(current_date())-day(C.fechaNacimiento)) as p FROM " + PersonaNatural.class.getName() + " C where C.fechaNacimiento is not null").setMaxResults(1).uniqueResult());
        //s.createQuery("SELECT tipoPersona.nombre as numeroSiniestros FROM "+DetalleSiniestro.class.getName()).list();

//        List l = s.createQuery(" SELECT DIAG.id "
//                + " FROM " + OrdenDePago.class.getName() + " O "
//                + " JOIN O.detalleSiniestros DES "
//                + " JOIN DES.diagnosticoSiniestros DIAG").list();


        List l = s.createQuery(" "
                + " FROM " + vista1.class.getName() + " O ").list();

        System.out.println(l);

        tx.commit();
        System.out.println("comit");
        s.close();
        System.out.println("close");
        System.out.println("Listo");
    }

    public static void main(String[] args) {
        new ConsultaPruebaOrlando();
    }
}
