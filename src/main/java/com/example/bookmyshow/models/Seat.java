package com.example.bookmyshow.models;

public class Seat {
    private int row;
    private int col;
    private int price;
    private boolean available = true;
    public Object seatMutex = new Object();
    Seat(int row, int col, int price, boolean available){
        this.row = row;
        this.col=col;
        this.price = price;
        this.available = available;
    }
    public void bookSeat(){

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
