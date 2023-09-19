package control;

import model.AutoUser;
import model.Cart;
import model.Confectionery;
import model.Review;
import model.OrderNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import repository.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ConfectioneryRepository confectioneryRepository;

    @RequestMapping(value="/")
    public String goHome(Model model){
        model.addAttribute("confectionery", confectioneryRepository.findAll());
        return "home";
    }

    @Autowired
    CartRepository cartRepository;

    @RequestMapping(value = "/confect/{id}", method=RequestMethod.GET)
    public String goInfo(@PathVariable("id") Integer id, Model model){
        model.addAttribute("reviews", reviewRepository.findByObjectIdLike(id));
        model.addAttribute("confect", confectioneryRepository.findOne(id));
        System.out.println(confectioneryRepository.findOne(id));
        return "confinfo";
    }

    @RequestMapping(value="/addNewConfect",method=RequestMethod.GET)
    public String makeNew(Model model)
    {
        Confectionery confect = new Confectionery();
        model.addAttribute("confect",confect);
        return "addNewConfect";
    }

    @RequestMapping(value="/basket",method = RequestMethod.GET)
    public String goBasket(Model model)
    {
        AutoUser user = (AutoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart=cartRepository.findByUserLike(user);
        System.out.println("2222");
        if(cart!=null){
            model.addAttribute("basket_objects",cart.getConfectList());}
        else
        {
            cart=new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return "basket";
    }

    @RequestMapping(value="/addNewConfect",method=RequestMethod.POST)
    public String submitNewConfect(@Valid @ModelAttribute Confectionery confectionery, Errors errors, Model model)
    {
        if(!errors.hasErrors()) {
            confectioneryRepository.save(confectionery);
            return "redirect:/";
        }
        else
        {
            System.out.println(errors);
            model.addAttribute("confect",confectionery);
            return "addNewConfect";
        }
    }

    @RequestMapping(value="/confect/{id}/review",method = RequestMethod.GET)
    public String goReview(@PathVariable("id") Integer id, Model model)
    {
        Review rvw=new Review();
        model.addAttribute("confect", confectioneryRepository.findOne(id));
        model.addAttribute("new_review", rvw);
        return "review";
    }

    @Autowired
    ReviewRepository reviewRepository;

    @RequestMapping(value="/confect/{idf}/review",method = RequestMethod.POST)
    public String postReview(@PathVariable("idf") Integer idf, @ModelAttribute Review rvw, Errors errors, Model model)
    {
        Confectionery product = confectioneryRepository.findOne(idf);
        if (rvw.getMark()!=null)
        {
            product.setK(product.getK() + 1);
            product.setSum(product.getSum() + rvw.getMark());
        }
        else {rvw.setMark(0);}
        rvw.setObject(product);
        AutoUser user = (AutoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        rvw.setUser(user);
        reviewRepository.save(rvw);
        confectioneryRepository.save(product);
        return "redirect:/";
    }


    @RequestMapping(value="/tobasket", method = RequestMethod.POST)
    public String toBasket(@RequestBody Integer id)
    {
        AutoUser user = (AutoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart = cartRepository.findByUserLike(user);
        Confectionery object = confectioneryRepository.findByIdLike(id);
        if(cart!=null){
            List<Confectionery> newlist=cart.getConfectList();
            newlist.add(object);
            cartRepository.save(cart);
        }
        else
        {
            cart = new Cart();
            cart.setUser(user);
            cart.getConfectList().add(object);
            cartRepository.save(cart);
        }
        return "redirect:/";
    }

    @RequestMapping(value="/basket",method = RequestMethod.POST)
    public String createOrder(Model model)
    {
        OrderNew newOrder = new OrderNew();
        AutoUser user = (AutoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newOrder.setConfectList(cartRepository.findByUserLike(user).getConfectList());
        newOrder.setUser(user);
        System.out.println(newOrder.getId());
        System.out.println(newOrder.getUser());
        System.out.println(newOrder.getConfectList());
        orderRepository.save(newOrder);
        Cart cart = cartRepository.findByUserLike(user);
        cart.setConfectList(null);
        cartRepository.save(cart);
        return "redirect:/";
    }

    @Autowired
    OrderNewRepository orderRepository;

    @Autowired
    AutoUserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute AutoUser user) {
        user.setRole("ROLE_USER");
        userRepository.save(user);

        //Program authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goRegister()
    {
        return "register";
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String goLogin()
    {
        return "login";
    }

    @RequestMapping(value = "/orderList",method = RequestMethod.GET)
    public String showOrders(Model model)
    {
        AutoUser user = (AutoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderNew> orderNewList = orderRepository.findByUserLike(user);
        model.addAttribute("orderList", orderNewList);
        return "orders";
    }

}
