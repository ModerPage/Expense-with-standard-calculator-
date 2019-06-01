package sample.ExpenseData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;

public class ExpenseData {
    private static final String EXPENSE_FILE = "expense.xml";

    private static final String EXPENSE = "expense";
    private static final String FOOD = "food";
    private static final String OTHER = "other";
    private static final String INCOME = "income";
    private static final String DATE = "date";
    private static final String TOTAL = "total";

    private ObservableList<Expense> expenses;

    public ExpenseData() {
        expenses = FXCollections.observableArrayList();
    }

    public ObservableList<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    public void loadExpenses() {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(EXPENSE_FILE);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            Expense expense = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if(event.isStartElement()) {
                    StartElement startElement = event.asStartElement();

                    if(startElement.getName().getLocalPart().equals(EXPENSE)) {
                        expense = new Expense();
                        continue;
                    }

                    if(event.isStartElement()) {
                        if(event.asStartElement().getName().getLocalPart().equals(FOOD)) {
                            event = eventReader.nextEvent();
                            expense.setFood(Double.parseDouble(event.asCharacters().getData()));
                            continue;
                        }
                    }

                    if(event.asStartElement().getName().getLocalPart().equals(OTHER)) {
                        event = eventReader.nextEvent();
                        expense.setOther(Double.parseDouble(event.asCharacters().getData()));
                        continue;
                    }

                    if(event.asStartElement().getName().getLocalPart().equals(INCOME)) {
                        event = eventReader.nextEvent();
                        expense.setIncome(Double.parseDouble(event.asCharacters().getData()));
                        continue;
                    }

                    if(event.asStartElement().getName().getLocalPart().equals(DATE)) {
                        event = eventReader.nextEvent();
                        expense.setDate(LocalDate.parse(event.asCharacters().getData()));
                        continue;
                    }

                    if(event.asStartElement().getName().getLocalPart().equals(TOTAL)) {
                        event = eventReader.nextEvent();
                        expense.setTotal(Double.parseDouble(event.asCharacters().getData()));
                        continue;
                    }
                }

                if(event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if(endElement.getName().getLocalPart().equals(EXPENSE)) {
                        expenses.add(expense);
                    }
                }
            }
        } catch (FileNotFoundException e) {

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void saveExpenses() {

        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(EXPENSE_FILE));

            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");

            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            StartElement expensesStartElement = eventFactory.createStartElement("","","expenses");
            eventWriter.add(expensesStartElement);
            eventWriter.add(end);

            for (Expense expense: expenses) {
                saveExpence(eventWriter,eventFactory,expense);
            }

            eventWriter.add(eventFactory.createEndElement("","","contacts"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println("Problem with Expenses file: " + e.getMessage());
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            System.out.println("Problem writing expense: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveExpence(XMLEventWriter eventWriter,XMLEventFactory eventFactory,Expense expense)
            throws FileNotFoundException,XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");

        StartElement configStartElement = eventFactory.createStartElement("","",EXPENSE);
        eventWriter.add(configStartElement);
        eventWriter.add(end);

        createNode(eventWriter, FOOD,"" + expense.getFood());
        createNode(eventWriter,OTHER,"" + expense.getOther());
        createNode(eventWriter,INCOME,"" + expense.getIncome());
        createNode(eventWriter,DATE,"" + expense.getDate());
        createNode(eventWriter,TOTAL,"" + expense.getTotal());

        eventWriter.add(eventFactory.createEndElement("","",EXPENSE));
        eventWriter.add(end);
    }

    private void createNode(XMLEventWriter eventWriter,String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        StartElement sElement = eventFactory.createStartElement("","",name);
        eventWriter.add(tab);
        eventWriter.add(sElement);

        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        EndElement eElement = eventFactory.createEndElement("","",name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }
}
