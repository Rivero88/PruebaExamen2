package com.example.pruebaexamen2.Data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModelAcademia: ViewModel() {
    private val _uiState = MutableStateFlow(UiStateAcademia())
    val uiState: StateFlow<UiStateAcademia> = _uiState.asStateFlow()

    var introducirHoras by mutableStateOf("")
        private set;

    fun nuevoValorHoras (nuevoIntroducirHoras:String){
        introducirHoras = nuevoIntroducirHoras
    }

    fun sumarHoras (asignatura: Asignatura, horasIntroducidas: String, asignaturas: ArrayList<Asignatura>){
        var horas = 1
        var textoUltAccionAct = ""
        var textoResumenAct = ""

        if(!"".equals(horasIntroducidas)){
            horas = horasIntroducidas.toInt()
        }
        asignatura.recuentoHoras += horas
        textoUltAccionAct = "Se han añadido ${horas} horas de la asignatura ${asignatura.nombre} con precio ${asignatura.precioHora} €."

        for (asig in asignaturas){
            if(asig.recuentoHoras > 0){
                textoResumenAct += "Asig: ${asig.nombre} precio hora: ${asig.precioHora} total horas: ${asig.recuentoHoras}\n"
            }
        }

        _uiState.update {
            actualizacionTexto -> actualizacionTexto.copy(
            textoUltAccion=textoUltAccionAct,
            textoResumen=textoResumenAct
            )
        }
    }
    fun restarHoras(asignatura: Asignatura, horasRestar: String, asignaturas: ArrayList<Asignatura>){
        var horas = 1
        var textoUltAccionAct: String = ""
        var textoResumenAct: String = ""

        if(!"".equals(horasRestar)){
            horas = horasRestar.toInt()
        }

        if(horas > 0){
            if(asignatura.recuentoHoras < horas){
                asignatura.recuentoHoras = 0
                textoUltAccionAct = "Se han eliminado ${horas} horas de la asignatura ${asignatura.nombre} con precio ${asignatura.precioHora} €."
            }else if(asignatura.recuentoHoras > 0) {
                asignatura.recuentoHoras -= horas
                textoUltAccionAct =
                    "Se han eliminado ${horas} horas de la asignatura ${asignatura.nombre} con precio ${asignatura.precioHora} €."
            }else if(asignatura.recuentoHoras==0){
                textoUltAccionAct =
                    "No se ha restado ningún valor."
            }
        }else {
            textoUltAccionAct =
                "No se ha restado ningún valor."
        }

        for (asig in asignaturas){
            if(asig.recuentoHoras > 0){
                textoResumenAct += "Asig: ${asig.nombre} precio hora: ${asig.precioHora} total horas: ${asig.recuentoHoras}\n"
            }
        }

        _uiState.update {
                actualizacionTexto -> actualizacionTexto.copy(
            textoUltAccion=textoUltAccionAct,
            textoResumen=textoResumenAct
        )
        }
    }
}