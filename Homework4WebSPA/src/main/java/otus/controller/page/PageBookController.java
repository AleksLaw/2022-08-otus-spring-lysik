package otus.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageBookController {
    @GetMapping("/book")
    public String allBookPage() {
        return "book";
    }
}