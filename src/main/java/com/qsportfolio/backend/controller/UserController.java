package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.user.FavoriteStock;
import com.qsportfolio.backend.request.users.ChangeEmailRequest;
import com.qsportfolio.backend.request.users.CreateFavoriteStockRequest;
import com.qsportfolio.backend.response.stock.StockResponseFactory;
import com.qsportfolio.backend.response.user.FavoriteStockResponse;
import com.qsportfolio.backend.service.user.FavoriteStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.service.user.UserService;
import com.qsportfolio.backend.service.user.FavoriteStockService;
import com.qsportfolio.backend.response.user.UserResponseFactory;
import com.qsportfolio.backend.response.user.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final FavoriteStockService favoriteStockService;

    public UserController(UserService userService,  FavoriteStockService favoriteStockService) {
        this.userService = userService;
        this.favoriteStockService = favoriteStockService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> returnUserData() {
        User user = userService.getUser();
        return ResponseEntity.ok(UserResponseFactory.createUserResponse(user));
    }

    @PutMapping("/email")
    public ResponseEntity<UserResponse> modifyUserEmail(@RequestBody ChangeEmailRequest request) {
        User newUser = userService.changeUserEmail(request.getEmail());
        return ResponseEntity.ok(UserResponseFactory.createUserResponse(newUser));
    }

    @PostMapping("/favoriteStock")
    public ResponseEntity<String> addFavoriteStock(@RequestBody CreateFavoriteStockRequest request){
        User user =  userService.getUser();
        favoriteStockService.addFavoriteStock(user, request.getStockSymbol());
        return ResponseEntity.ok("Successfully added favorite stock");
    }

    @DeleteMapping("/favoriteStock")
    public ResponseEntity<String> removeFavoriteStock(@RequestBody CreateFavoriteStockRequest request){
        User user =  userService.getUser();
        favoriteStockService.removeFavoriteStock(user, request.getStockSymbol());
        return ResponseEntity.ok("Successfully removed favorite stock");
    }

    @GetMapping("/favoriteStock")
    public ResponseEntity<FavoriteStockResponse> getFavoriteStock(){
        User user =  userService.getUser();
        List<Stock> stockList = favoriteStockService.getFavoriteStock(user);
        return ResponseEntity.ok(StockResponseFactory.createFavoriteStockResponse(stockList));
    }
}
