package com.exercise.ls.client;

import com.exercise.ls.model.Loan;
import com.exercise.ls.repository.LoanRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@SpringComponent
@UIScope
public class LoanEditor extends VerticalLayout implements KeyNotifier {

    @Autowired
    private LoanRepository repository;

    private Loan loan;

    private TextField id = new TextField("ID");
    private TextField amount = new TextField("Amount");
    private TextField interestRate = new TextField("Interest rate");
    private TextField length = new TextField("Length in months");
    private TextField monthlyPayment = new TextField("Monthly payment");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private final Map<String, Component> manualBoundComponents = new HashMap<>();

    private Binder<Loan> binder = new Binder<>(Loan.class);
    private ChangeHandler changeHandler;

    @Autowired
    public LoanEditor() {

        HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
        add(id, amount, interestRate, length,monthlyPayment, actions);

        manualBoundComponents.put("id", id);
        manualBoundComponents.put("amount", amount);
        manualBoundComponents.put("interestRate", interestRate);
        manualBoundComponents.put("length", length);
        manualBoundComponents.put("monthlyPayment", monthlyPayment);

        binder.forField(id)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter(Long.valueOf(0), ""))
                .bind(Loan::getId, Loan::setId);

        binder.forField(amount)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter(Long.valueOf(0), ""))
                .bind(Loan::getAmount, Loan::setAmount);

        binder.forField(interestRate)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(0, ""))
                .bind(Loan::getInterestRate, Loan::setInterestRate);

        binder.forField(length)
                .withNullRepresentation("")
                .withConverter(new StringToIntegerConverter(0, ""))
                .bind(Loan::getLength, Loan::setLength);

        binder.forField(monthlyPayment)
                .withNullRepresentation("")
                .withConverter(new StringToDoubleConverter(0.0, ""))
                .bind(Loan::getMonthlyPayment, Loan::setMonthlyPayment);


        binder.bindInstanceFields(this);


        add(manualBoundComponents.get("id"));
        add(manualBoundComponents.get("amount"));
        add(manualBoundComponents.get("interestRate"));
        add(manualBoundComponents.get("length"));
        add(manualBoundComponents.get("monthlyPayment"));

        id.setEnabled(false);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editLoan(loan));
        setVisible(false);
    }

    private void delete() {
        repository.delete(loan);
        changeHandler.onChange();
    }

    private void save() {
        repository.save(loan);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    final void editLoan(Loan loan) {
        if (loan == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = loan.getId() != null;
        if (persisted) {
            this.loan = repository.findById(loan.getId()).get();
        }
        else {
            this.loan = loan;
        }
        cancel.setVisible(persisted);

        binder.setBean(this.loan);

        setVisible(true);

        amount.focus();
    }

    void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
