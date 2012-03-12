package dataBase;

import java.util.ArrayList;
import java.util.Date;
import com.jswitch.base.modelo.Dominios;
import com.jswitch.base.modelo.HibernateUtil;
import com.jswitch.reporte.modelo.Reporte;
import com.jswitch.base.modelo.entidades.auditoria.AuditoriaBasica;
import com.jswitch.certificados.modelo.maestra.Certificado;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 *
 * @author bc
 */
public class ActualizarReportes_AND1 {

    public ActualizarReportes_AND1() {

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

        list.add(new Reporte(Dominios.CategoriaReporte.CERTIFICADOS, 0, "CERTIFICADO", "TITULO PRINCIPAL", "DESCRIPCION", "FROM " + Certificado.class.getName() + " as P WHERE P.id=18335", "Carta 8½ x 11 Vertical", false, true, true, false));                

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
        new ActualizarReportes_AND1();
    }
}
