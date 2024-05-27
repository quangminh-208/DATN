package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Tour;
import io.github.pudo58.base.service.TourService;
import io.github.pudo58.dto.TourSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tour")
@RequiredArgsConstructor
public class TourController extends AbstractController<Tour> {
    private final TourService tourService;

    @PostMapping("/findBySearch")
    public Page<Tour> findBySearch(@RequestBody TourSearchRequest model) {
        return this.tourService.findBySearch(model);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable UUID id) {
        this.tourService.deleteById(id);
    }
}
