package onlineShop.service;

import onlineShop.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CustomerDao;
import onlineShop.model.Customer;

@Service//跟 business logic 相关
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;// 通过 @Repository 创建的

    public void addCustomer(Customer customer) {
        customer.getUser().setEnabled(true);// 让 customer 可以登录

        Cart cart = new Cart();// cart 无法从页面得到，需要 new
        customer.setCart(cart);

        customerDao.addCustomer(customer);
    }

    public Customer getCustomerByUserName(String userName) {
        return customerDao.getCustomerByEmail(userName);
    }
}
