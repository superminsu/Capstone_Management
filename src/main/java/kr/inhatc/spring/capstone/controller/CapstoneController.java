package kr.inhatc.spring.capstone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.capstone.dto.CapstoneRequestDto;
import kr.inhatc.spring.capstone.service.CapstoneService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/capstone")
public class CapstoneController {

    private final CapstoneService capstoneService;

    @GetMapping("/list")
    public String getCapstoneListPage(Model model, @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {

        try {
            model.addAttribute("resultMap", capstoneService.findAll(page, size));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return "/capstone/list";
    }

    @GetMapping("/write")
    public String getCapstoneWritePage(Model model, CapstoneRequestDto capstoneRequestDto) {
        return "/capstone/write";
    }

    @GetMapping("/view")
    public String getCapstoneViewPage(Model model, CapstoneRequestDto capstoneRequestDto) throws Exception {

        try {
            if (capstoneRequestDto.getId() != null) {
                model.addAttribute("resultMap", capstoneService.findById(capstoneRequestDto.getId()));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return "/capstone/view";
    }

    @PostMapping("/write/action")
    public String capstoneWriteAction(Model model, CapstoneRequestDto capstoneRequestDto,
            MultipartHttpServletRequest multiRequest) throws Exception {

        try {
            if (!capstoneService.save(capstoneRequestDto, multiRequest)) {
                throw new Exception("#Exception boardWriteAction!");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return "redirect:/capstone/list";
    }

    @PostMapping("/view/action")
    public String capstoneViewAction(Model model, CapstoneRequestDto capstoneRequestDto,
            MultipartHttpServletRequest multiRequest) throws Exception {

        try {
            boolean result = capstoneService.updateCapstone(capstoneRequestDto, multiRequest);

            if (!result) {
                throw new Exception("#Exception boardViewAction!");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return "redirect:/capstone/list";
    }

    @PostMapping("/view/delete")
    public String capstoneViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {

        try {
            capstoneService.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return "redirect:/capstone/list";
    }

    @PostMapping("/delete")
    public String capstoneDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {

        try {
            capstoneService.deleteAll(deleteId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return "redirect:/capstone/list";
    }
}
