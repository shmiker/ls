package com.exercise.ls.client;

import com.exercise.ls.model.Loan;
import com.exercise.ls.repository.LoanRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.Collections;
import java.util.Optional;

@Route
public class MainView extends VerticalLayout {

    private final LoanRepository repo;

    private final Grid<Loan> grid;

    private final TextField filter;

    public MainView(LoanRepository repo, LoanEditor editor) {
        this.repo = repo;
        this.grid = new Grid<>(Loan.class);
        this.filter = new TextField();
        filter.setPlaceholder("Filter by ID");
        Button addNewBtn = new Button("New Loan", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "amount", "interestRate", "length", "monthlyPayment");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> getLoansList(e.getValue()));


        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editLoan(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editLoan(new Loan(0,0,0,0)));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            getLoansList(filter.getValue());
        });

        getLoansList("");
    }

    private void getLoansList(String filterText) {
        if (filterText.isEmpty()) {
            grid.setItems(repo.findAll());
        }
        else {
            Optional<Loan> loan = repo.findById(Long.valueOf(filterText));
            if (loan.isPresent()){
                grid.setItems(Collections.singletonList(loan.get()));
            } else {
                Notification.show("Loan with id " + filterText + " not found.");
            }


        }
    }


}