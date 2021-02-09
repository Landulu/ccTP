package borrow;


import java.util.ArrayList;

public interface IBorrowRepository {

    ArrayList<Borrow> getAll();

    void sync(ArrayList<Borrow> borrows);
}
