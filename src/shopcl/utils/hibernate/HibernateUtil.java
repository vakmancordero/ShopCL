package shopcl.utils.hibernate;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 *
 * @author Arturh
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        
        try {
            
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            
        } catch (Throwable ex) {
            
            throw new ExceptionInInitializerError(ex);
            
        }
        
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
