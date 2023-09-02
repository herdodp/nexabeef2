package com.fundamental.nexabeef

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


@Suppress("DEPRECATION")
class toolskerugianberat : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var tvResult: TextView
    private lateinit var kerugian: TextView
    private lateinit var btnCalculate: Button
    private lateinit var etInitialWeight: EditText
    private lateinit var etFinalWeight: EditText
    private lateinit var hargaawal: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolskerugianberat)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Kerugian berat"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        etInitialWeight = findViewById(R.id.etInitialWeight)
        etFinalWeight = findViewById(R.id.etFinalWeight)
        hargaawal = findViewById(R.id.hargaawal)
        tvResult = findViewById(R.id.tvResult)
        kerugian = findViewById(R.id.kerugian)
        btnCalculate = findViewById(R.id.btnCalculate)

        btnCalculate.setOnClickListener {
            calculateWeightLossPercentage()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun calculateWeightLossPercentage() {
        val initialWeightStr = etInitialWeight.text.toString()
        val finalWeightStr = etFinalWeight.text.toString()
        val hargaawal1 = hargaawal.text.toString()

        if (initialWeightStr.isNotEmpty() && finalWeightStr.isNotEmpty() && hargaawal1.isNotEmpty()) {
            val initialWeight = initialWeightStr.toDouble()
            val finalWeight = finalWeightStr.toDouble()
            val hargaawal = hargaawal1.toInt()

            val weightLossPercentage = calculateWeightLossPercentage(initialWeight, finalWeight)
            val kerugianrupiah = hargaawal * weightLossPercentage/100

            val decimalFormatSymbols = DecimalFormatSymbols().apply {
                groupingSeparator = '.'
            }

            val decimalFormatSymbols2 = DecimalFormatSymbols().apply {
                groupingSeparator = ','
            }

            val decimalFormat = DecimalFormat("#,##0", decimalFormatSymbols)

            val decimalFormat2 = DecimalFormat("#,##0", decimalFormatSymbols2)

            val weightlossFormatted2 = decimalFormat2.format(weightLossPercentage)

            val kerugianrupiahFormatted = decimalFormat.format(kerugianrupiah)


            tvResult.text = "Persentase kerugian berat: $weightlossFormatted2%"
            kerugian.text = "Kerugian: Rp $kerugianrupiahFormatted"
        }
    }

    private fun calculateWeightLossPercentage(initialWeight: Double, finalWeight: Double): Double {
        val weightLoss = initialWeight - finalWeight
        val weightLossPercentage = (weightLoss / initialWeight) * 100
        return weightLossPercentage.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
    }
}
