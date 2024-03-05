package com.example.proyectoiib.imccalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.proyectoiib.R
import com.example.proyectoiib.todoapp.TodoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class ImcCalculatorActivity : AppCompatActivity() {
    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currentWeight:Int = 70
    private var currentAge:Int = 25
    private var currentHeight:Int = 120

    private lateinit var cv1: CardView
    private lateinit var cv2: CardView
    private lateinit var tvHeight:TextView
    private lateinit var rsHeight:RangeSlider
    private lateinit var btnSubtractWeight:FloatingActionButton
    private lateinit var btnPlusWeight:FloatingActionButton
    private lateinit var tvWeigth:TextView
    private lateinit var btnSubtractAge:FloatingActionButton
    private lateinit var btnPlusAge:FloatingActionButton
    private lateinit var tvAge:TextView
    private lateinit var btnCalculate:Button

    private lateinit var icono_back:ImageView
    companion object{
        //Como si fuese atributo statico, todos pueden acceder a el
        const val IMC_KEY = "IMC_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)

        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        cv1 = findViewById(R.id.cv1)
        cv2 = findViewById(R.id.cv2)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        tvWeigth = findViewById(R.id.tvWeigth)
        btnSubtractAge = findViewById(R.id.btnSubtractAge)
        btnPlusAge= findViewById(R.id.btnPlusAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate=findViewById(R.id.btnCalculate)
        icono_back = findViewById(R.id.icono_back)
    }

    private fun initListeners() {
        icono_back.setOnClickListener {
            navigateToTodoApp()
        }
        cv1.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        cv2.setOnClickListener {
            changeGender()
            setGenderColor()
        }
        rsHeight.addOnChangeListener{_,value,_ ->
            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt()
            tvHeight.text = "$currentHeight cm"
        }
        btnPlusWeight.setOnClickListener{
            currentWeight++
            setWeight()
        }
        btnSubtractWeight.setOnClickListener{
            currentWeight--
            setWeight()
        }

        btnPlusAge.setOnClickListener{
            currentAge++
            setAge()
        }
        btnSubtractAge.setOnClickListener {
            currentAge--
            setAge()
        }

        btnCalculate.setOnClickListener{
            val result = calculateIMC()
            navigateToResultIMC(result)
        }
    }

    private fun navigateToTodoApp() {
        val intent = Intent(this, TodoActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToResultIMC(result:Double) {
        val intent = Intent(this,ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY,result)
        startActivity(intent)
    }

    private fun calculateIMC():Double{
        val df = DecimalFormat("#.##")
        val imc = currentWeight/(currentHeight.toDouble()/100 * currentWeight.toDouble()/100)
        return df.format(imc).toDouble()
    }

    private fun setWeight(){
        tvWeigth.text = currentWeight.toString()
    }
    private fun setAge(){
        tvAge.text = currentAge.toString()
    }

    private fun changeGender(){
        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun setGenderColor() {
        cv1.setBackgroundColor(getBackgroundColor(isMaleSelected))
        cv2.setBackgroundColor(getBackgroundColor(isFemaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent: Boolean):Int{
        val colorReference = if (isSelectedComponent) {
            R.color.backgorund_component_selected
        } else {
            R.color.backgorund_component
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun initUI(){
        setGenderColor()
        setWeight()
        setAge()
    }
}