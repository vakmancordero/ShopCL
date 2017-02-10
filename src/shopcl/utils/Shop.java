package shopcl.utils;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import shopcl.model.User;
import shopcl.utils.hibernate.HibernateUtil;

/**
 *
 * @author Arturh
 */
public class Shop {
    
    private SessionFactory sessionFactory;

    public Shop() {
        
    }
    
    /**
     * Permite generar una nueva sesión mediante
     * el sessionFactory
     * 
     * @return Una sesión de hibernate
     */
    private Session getSession() {
        return this.sessionFactory.openSession();
    }
    
    /**
     * Da acceso a un usuario administrador
     * 
     * @param username Nombre de usuario
     * @param password Contraseña de usuario
     * @return La existencia del usuario
     */
    public boolean login(String username, String password) {
        
        User user = null;
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = this.getSession();
        Transaction transaction = null;
        
        try {
            
            transaction = session.beginTransaction();
            
            /* Se obtiene un registro correspondiente al usuario y contraseña */
            Query query = session.createQuery(
                    "FROM User user WHERE "
                  + "user.username = :username AND "
                  + "user.password = :password"
            );

            query.setParameter("username", username);
            query.setParameter("password", password);
            
            user = (User) query.setMaxResults(1).uniqueResult();
            
            transaction.commit();
            
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
        } finally {
            
            session.close();
            
        }
        
        return  user != null ? 
                user.getUsername().equals(username) && 
                user.getPassword().equals(password) : false;
        
    }
    
    /**
     * Obtiene una lista de empleados
     * 
     * @param <T> The class entity to retrieve data
     * @param entity 
     * @return      lista de empleados
     * @see         Employee
     */
    public <T> List<T> getList(Class entity) {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        
        /* Se crea una lista para almacenar elementos */
        List<T> list = new ArrayList<>();
        
        /* Se obtienen los elementos existentes. */
        try {
            
            list = session.createCriteria(entity).list();
            
            transaction.commit();
             
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
        } finally {
            
            session.close();
            
        }
        
        return list;
        
    }
    
    public boolean save(Object object) {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        
        boolean saved = false;
        
        try {
            
            session.save(object);
            transaction.commit();
            
            saved = true;
             
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
            saved = false;
            
        } finally {
            
            session.close();
            
        }
        
        return saved;
        
    }
    
    public boolean delete(Object object) {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        
        boolean deleted = false;
        
        try {
            
            session.delete(object);
            transaction.commit();
            
            deleted = true;
             
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
            deleted = false;
            
        } finally {
            
            session.flush();
            session.close();
            
        }
        
        return deleted;
        
    }
    
    private void rollback(Transaction transaction) {
        if (transaction != null) transaction.rollback();
    }
    
}