import java.time.LocalDate;
import java.time.Period;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    // هنا قمنا بتعريف الأشياء التي سيتم وضعها في النافذة
    Label birthDateLabel, resultLabel;
    CheckBox deathDateCheckBox;
    Button calculateAgeButton;
    ComboBox dayOfBirth, monthOfBirth, yearOfBirth, dayOfDeath, monthOfDeath, yearOfDeath;

    // سنستخدم هذه المتغيرات لتخزين التواريخ التي إختارها المستخدم من النافذة بشكل مؤقت
    int d1, d2, m1, m2, y1, y2;

    // لتخزين تاريخ وفاته endDate لتخزين تاريخ ميلاد الشخص, و الكائن startDate سنستخدم الكائن
    LocalDate startDate, endDate;

    // سنستخدم هذه المتغيرات لعرض الفارق النهائي بين تاريخ الميلاد و التاريخ الحالي أو تاريخ الوفاة
    long daysCounter, monthsCounter, yearsCounter;

    
    // مفعلاً أو غير مفعل (deathDatePicker) قمنا ببناء هذه الدالة لتحديد متى سيكون الكائن الذي يمثل تاريخ الوفاة
    public void toggleDeathDatePicker() {
        
        // deathDatePicker يوجد عليه علامة صح, سيتم تفعيل الكائن deathDateCheckBox إذا كان الكائن
        if (deathDateCheckBox.isSelected()) {
            dayOfDeath.setDisable(false);
            monthOfDeath.setDisable(false);
            yearOfDeath.setDisable(false);
        } // deathDatePicker لا يوجد عليه علامة صح, سيتم إلغاء تفعيل الكائن deathDateCheckBox إذا كان الكائن
        else {
            dayOfDeath.setDisable(true);
            monthOfDeath.setDisable(true);
            yearOfDeath.setDisable(true);
        }
        
    }
    
    
    // قمنا ببناء هذه الدالة لحساب و عرض العمر سواء كان المستخدم يريد حساب عمر شخص على قيد الحياة أو شخص ميت
    public void displayAge() {
        
        // سنستخدم هذا المتغير لتخزين نص النتيجة التي ستظهر
        String text = "";

        // هنا قمنا بتخزين تاريخ الميلاد الذي إختاره المستخدم في المتغيرات التالية
        y1 = Integer.parseInt(yearOfBirth.getSelectionModel().getSelectedItem().toString());
        m1 = monthOfBirth.getSelectionModel().getSelectedIndex() + 1;
        d1 = Integer.parseInt(dayOfBirth.getSelectionModel().getSelectedItem().toString());
        
        // إذا كان خيار تحديد تاريخ الوفاة مفعل, سيتم تخزين التاريخ المحدد في المتغيرات التالية
        if (deathDateCheckBox.isSelected()) {
            y2 = Integer.parseInt(yearOfDeath.getSelectionModel().getSelectedItem().toString());
            m2 = monthOfDeath.getSelectionModel().getSelectedIndex() + 1;
            d2 = Integer.parseInt(dayOfDeath.getSelectionModel().getSelectedItem().toString());
        } // إذا كان خيار تحديد تاريخ الوفاة غير مفعل, سيتم تخزين التاريخ الحالي في المتغيرات التالية
        else {
            y2 = LocalDate.now().getYear();
            m2 = LocalDate.now().getMonthValue();
            d2 = LocalDate.now().getDayOfMonth();
        }

        // endDate و تاريخ الوفاة في الكائن startDate هنا قمنا بوضع تاريخ الميلاد الذي حدده المستخدم في الكائن
        startDate = LocalDate.of(y1, m1, d1);
        endDate = LocalDate.of(y2, m2, d2);

        // هنا قمنا بتخزين الفارق بين التواريخ في المتغيرات التالية
        yearsCounter = Period.between(startDate, endDate).getYears();
        monthsCounter = Period.between(startDate, endDate).getMonths();
        daysCounter = Period.between(startDate, endDate).getDays();

        // هنا قلنا إذا كان التاريخين متواسيين سيتم إظهار التنبيه التالي
        if (yearsCounter == 0 && monthsCounter == 0 && daysCounter == 0) {
            resultLabel.setTextFill(Color.RED);
            resultLabel.setText("Cannot compare same date!");
        } // هنا قلنا أنه إذا كانت التواريخ المحددة هي منطقياً صحيحة سيتم إظهار العمر
        else if (!Period.between(startDate, endDate).isNegative()) {

            if (yearsCounter == 1) {
                text += yearsCounter + " year ";
            } else if (yearsCounter > 1) {
                text += yearsCounter + " years ";
            }

            if (monthsCounter == 1) {
                if (yearsCounter > 0 && daysCounter > 0) {
                    text += ", " + monthsCounter + " month ";
                } else if (yearsCounter > 0 && daysCounter == 0) {
                    text += "and " + monthsCounter + " month ";
                } else {
                    text += monthsCounter + " month ";
                }
            }

            if (monthsCounter > 1) {
                if (yearsCounter > 0 && daysCounter > 0) {
                    text += ", " + monthsCounter + " months ";
                } else if (yearsCounter > 0 && daysCounter == 0) {
                    text += "and " + monthsCounter + " months ";
                } else {
                    text += monthsCounter + " months ";
                }
            }

            if (daysCounter == 1) {
                if (yearsCounter == 0 && monthsCounter == 0) {
                    text += daysCounter + " day";
                } else {
                    text += "and " + daysCounter + " day";
                }
            }

            if (daysCounter > 1) {
                if (yearsCounter == 0 && monthsCounter == 0) {
                    text += daysCounter + " days";
                } else {
                    text += "and " + daysCounter + " days";
                }
            }

            resultLabel.setTextFill(Color.BLACK);
            resultLabel.setText(text);
        } // هنا قلنا أنه إذا كانت التواريخ المحددة هي منطقياً غير مقبولة سيتم إظهار التنبيه التالي
        else {
            resultLabel.setTextFill(Color.RED);
            resultLabel.setText("Logic order of Dates is wrong!");
        }

    }
    

    @Override
    public void start(Stage stage) {
        
        // هنا وضعنا أسماء الأشهر التي سنظهرها في قائمة الأشهر
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
 
        // هنا وضعنا أرقام الأيام التي سنظهرها في قائمة الأيام
        Integer[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
 
        // currentYear هنا قمنا بتخزين رقم السنة الحالية في المتغير
        int currentYear = LocalDate.now().getYear();
 
        // هنا قمنا بإنشاء مصفوفة, عدد عناصرها يساوي رقم السنة الحالية
        Integer[] years = new Integer[currentYear];
 
        // هنا قمنا بتخزين أرقام السنين من 1 إلى رقم السنة الحالية بشكل عكسي
        for (int i = 0; i < currentYear; i++)
            years[currentYear-i-1] = i + 1;
        
        // هنا قمنا بإنشاء جميع الأشياء التي سنضيفها في النافذة
        birthDateLabel = new Label("Birth Date");
        deathDateCheckBox = new CheckBox("Death Date");
        resultLabel = new Label();
        calculateAgeButton = new Button("Calculate Age");
        dayOfBirth = new ComboBox(FXCollections.observableArrayList(days));
        monthOfBirth = new ComboBox(FXCollections.observableArrayList(months));
        yearOfBirth = new ComboBox(FXCollections.observableArrayList(years));
        dayOfDeath = new ComboBox(FXCollections.observableArrayList(days));
        monthOfDeath = new ComboBox(FXCollections.observableArrayList(months));
        yearOfDeath = new ComboBox(FXCollections.observableArrayList(years));
        
        // هنا قما بتعديل حجم جميع الأشياء التي سنضيفها في النافذة
        birthDateLabel.setPrefSize(150, 30);
        deathDateCheckBox.setPrefSize(150, 30);
        dayOfBirth.setPrefSize(65, 30);
        monthOfBirth.setPrefSize(65, 30);
        yearOfBirth.setPrefSize(70, 30);
        dayOfDeath.setPrefSize(65, 30);
        monthOfDeath.setPrefSize(65, 30);
        yearOfDeath.setPrefSize(70, 30);
        calculateAgeButton.setPrefSize(215, 50);
        resultLabel.setPrefSize(430, 30);
        
        birthDateLabel.setTranslateX(174);
        birthDateLabel.setTranslateY(40);
        dayOfBirth.setTranslateX(110);
        dayOfBirth.setTranslateY(80);
        monthOfBirth.setTranslateX(182.5);
        monthOfBirth.setTranslateY(80);
        yearOfBirth.setTranslateX(255);
        yearOfBirth.setTranslateY(80);
        deathDateCheckBox.setTranslateX(150);
        deathDateCheckBox.setTranslateY(150);
        dayOfDeath.setTranslateX(110);
        dayOfDeath.setTranslateY(190);
        monthOfDeath.setTranslateX(182.5);
        monthOfDeath.setTranslateY(190);
        yearOfDeath.setTranslateX(255);
        yearOfDeath.setTranslateY(190);
        calculateAgeButton.setTranslateX(110);
        calculateAgeButton.setTranslateY(260);
        resultLabel.setTranslateX(0);
        resultLabel.setTranslateY(340);
        
        // يظهر جهة الوسط resultLabel هنا جعلنا نص الكائن
        resultLabel.setAlignment(Pos.CENTER);
        
        dayOfBirth.getSelectionModel().selectFirst();
        monthOfBirth.getSelectionModel().selectFirst();
        yearOfBirth.getSelectionModel().select(0);
        
        dayOfDeath.getSelectionModel().selectFirst();
        monthOfDeath.getSelectionModel().selectFirst();
        yearOfDeath.getSelectionModel().select(0);

        // هنا قما بتعديل حجم و سمك خط جميع الأشياء التي سنضيفها في النافذة
        birthDateLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
        deathDateCheckBox.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
        resultLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
        calculateAgeButton.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        // في النافذة لأننا ننوي ترتيب العناصر فيه بشكل يدويRoot Node و الذي ننوي جعله الـ Group هنا قمنا بإنشاء كائن من الكلاس
        Group root = new Group();

        // هنا قمنا بإضافة جميع الأشياء التي قمنا بإنشائها في النافذة
        root.getChildren().add(birthDateLabel);
        root.getChildren().add(deathDateCheckBox);
        root.getChildren().add(resultLabel);
        root.getChildren().add(calculateAgeButton);
        root.getChildren().add(dayOfBirth);
        root.getChildren().add(monthOfBirth);
        root.getChildren().add(yearOfBirth);
        root.getChildren().add(dayOfDeath);
        root.getChildren().add(monthOfDeath);
        root.getChildren().add(yearOfDeath);

        // فيها و تحديد حجمها Node كأول root هنا قمنا بإنشاء محتوى النافذة مع تعيين الكائن
        Scene scene = new Scene(root, 430, 420);
        
        // هنا وضعنا عنوان للنافذة
        stage.setTitle("Age Calculator");
        
        // أي وضعنا محتوى النافذة الذي قمنا بإنشائه للنافذة .stage في كائن الـ scene هنا وضعنا كائن الـ
        stage.setScene(scene);
        
        // هنا قمنا بإظهار النافذة
        stage.show();
        
        stage.setResizable(false);

        // calculateAgeButton هنا قمنا بتحديد ماذا سيحدث عند النقر على الكائن
        // حتى تحسب و تعرض العمر displayAge() فعلياً سيتم إستدعاء الدالة 
        calculateAgeButton.setOnAction((Action) -> {
            displayAge();
        });

        // deathDateCheckBox هنا قمنا بتحديد ماذا سيحدث عند النقر على الكائن
        // deathDatePicker لتفعيل أو إلغاء تفعيل الكائن toggleDeathDatePicker() فعلياً سيتم إستدعاء الدالة 
        deathDateCheckBox.selectedProperty().addListener(
            (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                toggleDeathDatePicker();
            }
        );

        // غير مفعل بشكل إفتراضي عند تشغيل البرنامج toggleDeathDatePicker لجعل الكائن toggleDeathDatePicker() هنا قمنا باستدعاء الدالة
        toggleDeathDatePicker();
    }

    // هنا قمنا بتشغيل التطبيق
    public static void main(String[] args) {
        launch(args);
    }

}
