
package onlineShop.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Authorities;
import onlineShop.model.Customer;
import onlineShop.model.User;

@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addCustomer(Customer customer) {
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmailId(customer.getUser().getEmailId());
        Session session = null;

        try {
            session = sessionFactory.openSession();//session 用来对DB操作
            session.beginTransaction();//开始原子操作，涉及多张表的级联操作用 transaction
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit();//操作结束，保证相关联的表被原子执行
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();//回滚到操作之前的状态
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Customer getCustomerByEmail(String email) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {

            Criteria criteria = session.createCriteria(User.class);//criteria = 搜索条件、filter
            user = (User) criteria.add(Restrictions.eq("emailId", email)).uniqueResult();// eq() equal,uniqueResult() 找到一个就返回了
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null)
            return user.getCustomer();
        return null;
    }
}

