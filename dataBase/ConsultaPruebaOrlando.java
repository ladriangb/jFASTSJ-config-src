package dataBase;

import com.jswitch.base.modelo.HibernateUtil;

import com.jswitch.base.modelo.entidades.defaultData.ConfiguracionesGenerales;
import com.jswitch.pagos.modelo.maestra.Factura;
import java.util.List;
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

        int aPartirDe = 7500;
        String sql = "SELECT nombre FROM " + ConfiguracionesGenerales.class.getName()
                + " C LIMIT 1";

        List l=s.createQuery(sql).list();
        System.out.println(l.size());
                Double baseIslr=0d;
        System.out.println(baseIslr);
        String update = "UPDATE "
                + Factura.class.getName()
                + " SET sustraendo="
                + " CAST(baseIslr/:base*:base2*porcentajeRetencionIslr AS big_decimal)"
                + " WHERE  id IN (SELECT C.id FROM " + Factura.class.getName()
                + " C WHERE C.detalleSiniestro.id=:det and C.tipoConceptoSeniat.codigo=:co)";
        s.beginTransaction();
        s.createQuery(update).
                setDouble("base", baseIslr).
                setDouble("base2", aPartirDe).
                setLong("det", 95014l).
                setString("co", "004").
                executeUpdate();
        s.getTransaction().commit();
        System.out.println("comit");
        s.close();
        System.out.println("close");
        System.out.println("Listo");
    }

    public static void main(String[] args) {
        new ConsultaPruebaOrlando();
    }
}
