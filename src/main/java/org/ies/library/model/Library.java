package org.ies.library.model;

import java.util.*;

public class Library {
    private String name;
    private Map<String, Book> booksByIsbn;

    private List<Customer> customers;

    private TreeSet<BookLend> bookLends;

    public Library(String name, Map<String, Book> booksByIsbn, List<Customer> customers, TreeSet<BookLend> bookLends) {
        this.name = name;
        this.booksByIsbn = booksByIsbn;
        this.customers = customers;
        this.bookLends = bookLends;
    }

    public Library() {
    }

    public List<Book> findBooksByGenre(String genre) {
        List<Book> genreBooks = new ArrayList<>();
        for (Book book : booksByIsbn.values()) {
//            ESTA SOLUCIÓN NO ES ÓPTIMA Y ES MÁS DIFÍCIL DE HACER
//            for(String bookGenre: book.getGenres()) {
//                if(bookGenre.equals(genre)) {
//                    genreBooks.add(book);
//                }
//            }
            if (book.getGenres().contains(genre)) {
                genreBooks.add(book);
            }
        }

        return genreBooks;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Book> getBooksByIsbn() {
        return booksByIsbn;
    }

    public void setBooksByIsbn(Map<String, Book> booksByIsbn) {
        this.booksByIsbn = booksByIsbn;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public TreeSet<BookLend> getBookLends() {
        return bookLends;
    }

    public void setBookLends(TreeSet<BookLend> bookLends) {
        this.bookLends = bookLends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Library library = (Library) o;

        if (!Objects.equals(name, library.name)) return false;
        if (!Objects.equals(booksByIsbn, library.booksByIsbn)) return false;
        if (!Objects.equals(customers, library.customers)) return false;
        return Objects.equals(bookLends, library.bookLends);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (booksByIsbn != null ? booksByIsbn.hashCode() : 0);
        result = 31 * result + (customers != null ? customers.hashCode() : 0);
        result = 31 * result + (bookLends != null ? bookLends.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", booksByIsbn=" + booksByIsbn +
                ", customers=" + customers +
                ", bookLends=" + bookLends +
                '}';
    }

    public List<Customer> findMembers(int zipCode) {
        List<Customer> customersByZipCode = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getZipCode() == zipCode) {
                customersByZipCode.add(customer);
            }
        }
        return customersByZipCode;
    }

    public void createLend(String nif, String isbn) {
        bookLends.add(new BookLend(isbn, new Date(), nif));
    }

    public void removeGenreBook(String isbn, String genre) {

        /* Mas dificil, mas tardado y se tarda mas en ejecutar
        for (Book book : booksByIsbn.values()) {
            if (book.getIsbn().equals(isbn)) {
                book.getGenres().remove(genre);
            }
        }
        */

        if (booksByIsbn.containsKey(isbn)) {
            Book book = booksByIsbn.get(isbn);
            book.getGenres().remove(genre);
        } else {
            System.out.println("no existe el libro");
        }
    }

    public boolean ifExistLend(String isbn, int number) {
       /*
        for (BookLend lend : bookLends) {
            if (lend.getIsbn().equals(isbn)) {
            //si no existe tal isbn en prestamos
                for (Customer customer : customers) {
                    if (customer.getCustomerNumber() == number) {
                    //si existe el numero
                        if (lend.getNif().equals(customer.getNif())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
        */
        Customer customer = findCustomerByNumber(number); // metodo creado
        if (customer != null) {
            for (var bookLend : bookLends) {
                if (bookLend.getNif().equals(customer.getNif()) && bookLend.getIsbn().equals(isbn)) {
                    return true;
                }
            }
        }
        return false;

    }

    public Customer findCustomerByNumber(int number) {
        for (Customer customer : customers) {
            if (customer.getCustomerNumber() == number) {
                return customer;
            }

        }
        return null;
    }
}
