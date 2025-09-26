package com.qsportfolio.backend.service.user;

import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.user.FavoriteStock;
import com.qsportfolio.backend.repository.StockRepository;
import com.qsportfolio.backend.repository.FavoriteStockRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.qsportfolio.backend.response.user.FavoriteStockResponse;
import org.springframework.stereotype.Service;

@Service
public class FavoriteStockService {

    private final StockRepository stockRepository;
    private final FavoriteStockRepository favoriteStockRepository;

    public FavoriteStockService( StockRepository stockRepository, FavoriteStockRepository favoriteStockRepository) {
        this.stockRepository = stockRepository;
        this.favoriteStockRepository = favoriteStockRepository;
    }

    public  void addFavoriteStock(User user, String stockSymbol){
        Optional<Stock> stock = this.stockRepository.findBySymbol(stockSymbol);

        if (stock.isEmpty()) {
            throw new IllegalArgumentException("Stock with symbol " + stockSymbol + " not found");
        }
        UUID stockId = stock.get().getId();
        FavoriteStock favoriteStock = new FavoriteStock();
        favoriteStock.setUserId(user.getId());
        favoriteStock.setStockId(stockId);
        this.favoriteStockRepository.save(favoriteStock);

    }

    public  void removeFavoriteStock(User user, String stockSymbol){
        Optional<Stock> stock = this.stockRepository.findBySymbol(stockSymbol);

        if (stock.isEmpty()) {
            throw new IllegalArgumentException("Stock with symbol " + stockSymbol + " not found");
        }
        UUID stockId = stock.get().getId();
        UUID userId = user.getId();
        FavoriteStock favoriteStock = this.favoriteStockRepository.findByStockIdAndUserId(stockId, userId);
        this.favoriteStockRepository.delete(favoriteStock);
    }

    public List<Stock> getFavoriteStock(User user) {
        UUID userId = user.getId();
        List<FavoriteStock> favoriteStocks = this.favoriteStockRepository.findByUserId(userId);
        List<UUID> stocksId = favoriteStocks.stream().map(FavoriteStock::getStockId).toList();
        return stocksId.stream()
                .map(stockRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}


