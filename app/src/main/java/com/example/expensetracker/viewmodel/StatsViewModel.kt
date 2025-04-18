package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.Utils
import com.example.expensetracker.data.ExpenseDataBase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.data.model.ExpenseSummary
import com.github.mikephil.charting.data.Entry


class StatsViewModel(val dao:ExpenseDao):ViewModel() {
    val entries = dao.getAllExpenses()
    val topEntries = dao.getTopExpenses()

    fun getEntriesForChart(entries: List<ExpenseEntity>): List<Entry> {
        val list = mutableListOf<Entry>()
        for (entry in entries) {
            //val formattedDate = Utils.getMillisFromDate(entry.date)
            if(entry.type!="Income"){
                list.add(Entry(entry.date.toFloat(), entry.amount.toFloat()))
            }

        }
        return list
    }
}

class StatsViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StatsViewModel::class.java)){
            val dao= ExpenseDataBase.getDataBase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}