package com.example.cours3dkokorin.controllers;

import com.example.cours3dkokorin.model.ColorSock;
import com.example.cours3dkokorin.model.Size;
import com.example.cours3dkokorin.model.Socks;
import com.example.cours3dkokorin.model.TypeOperation;
import com.example.cours3dkokorin.services.ProductServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/socks")
@Tag(name = "Носки", description = "работаем с носками")
public class SocksController {

    private final ProductServices socksServices;

    public SocksController(ProductServices socksServices) {
        this.socksServices = socksServices;
    }

    @PostMapping(value = "/api/socks")
    @Operation(summary = "Добавляем носки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Внесен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Socks.class))

                            )
                    })
    })
    public ResponseEntity<?> addSockOnWarehouse(
            @RequestParam ColorSock colorSock, @RequestParam Size size,
            @RequestParam int cotton, @RequestParam int quantity) {
        if (!StringUtils.isBlank(colorSock.name())) {
            Socks socks = new Socks(colorSock, size, cotton, quantity);
            return ResponseEntity.ok().body(socksServices.addProduct(socks));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/put/socks")
    @Operation(summary = "Отпускаем со склада носки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Внесен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Socks.class))

                            )
                    })
    })
    public ResponseEntity<?> putSockOnWarehouse(
            @RequestParam ColorSock colorSock, @RequestParam Size size,
            @RequestParam int cotton, @RequestParam int quantity) {
        if (!StringUtils.isBlank(colorSock.name())) {
            Socks socks = new Socks(colorSock, size, cotton, quantity);
            return ResponseEntity.ok().body(socksServices.putAndDeleteProduct(socks,TypeOperation.PUT));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/delete/socks")
    @Operation(summary = "Списание со склада")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Списаны",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Socks.class))

                            )
                    })
    })
    public ResponseEntity<?> deleteSockOnWarehouse(
            @RequestParam ColorSock colorSock, @RequestParam Size size,
            @RequestParam int cotton, @RequestParam int quantity) {
        if (!StringUtils.isBlank(colorSock.name())) {
            Socks socks = new Socks(colorSock, size, cotton, quantity);
            return ResponseEntity.ok().body(socksServices.putAndDeleteProduct(socks,TypeOperation.DELETE));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/socks/min")
    @Operation(summary = "Остаток носков на складе хлопком меньше N")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Работает",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Socks.class))

                            )
                    })
    })
    public String getSockCottonMin(@RequestParam ColorSock colorSock,
                                   @RequestParam int cottonMin) {
        return socksServices.getSockColorMinCotton(colorSock, cottonMin);
    }

    @GetMapping("/api/socks/max")
    @Operation(summary = "Остаток носков на складе хлопком БОЛЬШЕ N")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Работает",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Socks.class))

                            )
                    })
    })
    public String getSockCottonMax(@RequestParam ColorSock colorSock,
                                   @RequestParam int cottonMax) {
        return socksServices.getSockColorMaxCotton(colorSock, cottonMax);
    }


}
