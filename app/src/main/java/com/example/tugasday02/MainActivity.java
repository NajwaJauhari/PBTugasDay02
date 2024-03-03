package com.example.tugasday02;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String, Integer> itemPrices;
    private CheckBox bracelet1, bracelet2, bracelet3;
    private EditText editBuyerName, editQuantityPulsa, editQuantityToken, editQuantityEmoney;
    private RadioGroup radioMembership;
    private Button btnProcess;
    private TextView tvReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemPrices = new HashMap<>();
        itemPrices.put("Braided Bracelets", 30000);
        itemPrices.put("Floral Wooden Bracelet", 40000);
        itemPrices.put("Polymer Clay Heart Bracelets", 50000);

        bracelet1 = findViewById(R.id.bracelet1);
        bracelet2 = findViewById(R.id.bracelet2);
        bracelet3 = findViewById(R.id.bracelet3);
        editBuyerName = findViewById(R.id.editBuyerName);
        editQuantityPulsa = findViewById(R.id.editQuantityPulsa);
        editQuantityToken = findViewById(R.id.editQuantityToken);
        editQuantityEmoney = findViewById(R.id.editQuantityEmoney);
        radioMembership = findViewById(R.id.radioMembership);
        btnProcess = findViewById(R.id.btnProcess);
        tvReceipt = findViewById(R.id.tvReceipt);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processTransaction();
            }
        });
    }

    private void processTransaction() {
        StringBuilder receipt = new StringBuilder("Receipt\n\n");
        int totalOverallCost = 0; // Variabel untuk menyimpan biaya total keseluruhan

        // Dapatkan nama pembeli
        String buyerName = editBuyerName.getText().toString();

        // Sertakan nama pembeli dalam struk
        receipt.append("Nama Pembeli: ").append(buyerName).append("\n\n");

        // Proses Pulsa
        if (bracelet1.isChecked()) {
            totalOverallCost += processItem("Braided Bracelets", editQuantityPulsa, receipt);
        }

        // Proses Token
        if (bracelet2.isChecked()) {
            totalOverallCost += processItem("Floral Wooden Bracelet", editQuantityToken, receipt);
        }

        // Proses Emoney
        if (bracelet3.isChecked()) {
            totalOverallCost += processItem("Polymer Clay Heart Bracelets", editQuantityEmoney, receipt);
        }

        // Tampilkan biaya total keseluruhan
        receipt.append("Biaya Total Keseluruhan: Rp.").append(totalOverallCost).append("\n\n");

        // Tampilkan struk
        tvReceipt.setText(receipt.toString());
    }

    private int processItem(String itemName, EditText quantityEditText, StringBuilder receipt) {
        int quantity = Integer.parseInt(quantityEditText.getText().toString());
        int itemPrice = itemPrices.get(itemName);
        int adminFee = getAdminFee(itemName);

        boolean isMember = radioMembership.getCheckedRadioButtonId() == R.id.radioMember;

        int totalCost = calculateTotalCost(itemPrice, quantity, isMember, adminFee);

        int discount = calculateDiscount(itemPrice, quantity, isMember, adminFee);
        int discountedTotal = totalCost - discount;

        receipt.append("Item: ").append(itemName).append("\n");
        receipt.append("Jumlah: ").append(quantity).append("\n");
        receipt.append("Harga Barang: Rp.").append(itemPrice).append("\n");
        receipt.append("Biaya Admin: Rp.").append(adminFee).append("\n");
        receipt.append("Membership: ").append(isMember ? "Member (diskon 5%)" : "Non-Member").append("\n");
        receipt.append("Diskon: 5%").append("\n");
        receipt.append("Pemotongan Harga: Rp.").append(discount).append("\n");
        receipt.append("Biaya Total: Rp.").append(discountedTotal).append("\n\n");

        return discountedTotal; // Kembalikan biaya total setelah diskon untuk item ini
    }

    private int calculateTotalCost(int itemPrice, int quantity, boolean isMember, int adminFee) {
        int totalCost = itemPrice * quantity;

        totalCost += adminFee; // Tambahkan biaya admin

        return totalCost;
    }

    private int calculateDiscount(int itemPrice, int quantity, boolean isMember, int adminFee) {
        int totalCost = (itemPrice * quantity) + adminFee;

        if (isMember) {
            int discount = (int) (totalCost * 0.05); // Diskon 5% untuk member
            return discount;
        }

        return 0;
    }

    private int getAdminFee(String itemName) {
        switch (itemName) {
            case "Braided Bracelets":
                return 2000;
            case "Floral Wooden Bracelet":
                return 3000;
            case "Polymer Clay Heart Bracelets":
                return 2500;
            default:
                return 0;
        }
    }
}