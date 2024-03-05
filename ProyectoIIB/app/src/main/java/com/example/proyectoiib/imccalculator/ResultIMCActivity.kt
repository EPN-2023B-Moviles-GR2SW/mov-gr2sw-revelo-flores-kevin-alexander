package com.example.proyectoiib.imccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.proyectoiib.R
import com.example.proyectoiib.imccalculator.ImcCalculatorActivity.Companion.IMC_KEY

class ResultIMCActivity : AppCompatActivity() {
    private lateinit var tvResult:TextView
    private lateinit var tvIMC:TextView
    private lateinit var tvDescirption:TextView
    private lateinit var btnRecalculate:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)

        val result:Double = intent.extras?.getDouble(IMC_KEY) ?: -1.0

        initComponents()
        initUI(result)
        initListeners()
    }

    private fun initListeners() {
        btnRecalculate.setOnClickListener { onBackPressed() }
    }

    private fun initComponents(){
        tvIMC=findViewById(R.id.tvIMC)
        tvResult = findViewById(R.id.tvResult)
        tvDescirption = findViewById(R.id.tvDescription)
        btnRecalculate = findViewById(R.id.btnRecalculate)
    }

    private fun initUI(result: Double) {
        tvIMC.text = result.toString()
        when(result){
            in 0.00 ..18.50 ->{//bajo peso
                tvResult.text = getString(R.string.title_bajo_peso)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.peso_bajo))
                tvDescirption.text = getString(R.string.description_bajo_peso)
            }
            in 18.51..24.99->{//peso normal
                tvResult.text = getString(R.string.title_peso_normal)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.peso_normal))
                tvDescirption.text = getString(R.string.description_peso_normal)
            }
            in 25.00..29.99->{//sobrepeso
                tvResult.text = getString(R.string.title_sobrepeso)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.soprepeso))
                tvDescirption.text = getString(R.string.description_sobrepeso)
            }
            in 30.00 ..99.00->{//obesidad
                tvResult.text = getString(R.string.title_obesidad)
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.obesidad))
                tvDescirption.text = getString(R.string.description_obesidad)
            }
            else ->{//Error (si devuleve -1)
                tvIMC.text = getString(R.string.Error)
                tvResult.text = getString(R.string.Error)
                tvDescirption.text = getString(R.string.Error)
            }
        }
    }

}