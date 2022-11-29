package com.equitel.pruebaequitel.Sheet8

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.api.CloudFunctions
import com.equitel.pruebaequitel.database.getDataBase
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class Sheet8ViewModel (application: Application) : AndroidViewModel(application){


    /*private var pdfFileName: File
    private var dirPath: String
    private var fileName: String


    init {
        dirPath = "${fileDir}/cert/pdffiles"
        val dirFile = File(dirPath)
        if(!dirFile.exists()) {
            dirFile.mkdirs()
        }
        fileName = "DemoGraphs.pdf"
        val file = "${dirPath}/${fileName}"
        pdfFileName = File(file)
        if(pdfFileName.exists()) {
            pdfFileName.delete()
        }
    }*/
    var isFileReadyObserver = MutableLiveData<Boolean>()


    private val dataBase = getDataBase(application)
    private val repository = Sheet8Repository(dataBase)



    private val _Almacenamiento = MutableLiveData<Almacenamiento>()
    val almacenamiento : LiveData<Almacenamiento>
        get()= _Almacenamiento


    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean>
        get() =_status

    fun extraerAlmacenamiento(){
        viewModelScope.launch {
            _Almacenamiento.value = repository.extraerAlmacenamiento()
        }
    }

    fun guardaralmacenamiento(almacenamiento: Almacenamiento){
        viewModelScope.launch {
            repository.guardarAlmacenamiento(almacenamiento)
        }
    }

    fun consultarID(id : Int?){
        viewModelScope.launch {
            _Almacenamiento.value =  repository.consultarID(id)
        }
    }

    /*fun envioDataAlmacenamientoPdf(almacenamiento: Almacenamiento){
        viewModelScope.launch {
            repository.enviarDataPdf(almacenamiento)
        }
    }*/

    /*fun downloadPdfFile(almacenamiento: Almacenamiento, pdfFileName : File) {
        viewModelScope.launch {
            CloudFunctions.retrofitService.postEnvioPdf(almacenamiento).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    isFileReadyObserver.postValue(false)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("====", "====response : " +response)
                    Log.e("====", "====response : " +response.isSuccessful)
                    if(response.isSuccessful) {
                        val result = response.body()?.byteStream()
                        result?.let {
                            writeToFile(it, pdfFileName)
                        }?:kotlin.run {
                            isFileReadyObserver.postValue(false)
                        }
                    }
                    else
                        isFileReadyObserver.postValue(false)
                }
            })
        }
    }*/

    fun getdownloadPdfFile(pdfFileName : File, almacenamiento: Almacenamiento) {
        viewModelScope.launch {
            CloudFunctions.retrofitService.postEnvioPdf(almacenamiento).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    isFileReadyObserver.postValue(false)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("====", "====response : " +response)
                    Log.e("====", "====response : " +response.isSuccessful)
                    if(response.isSuccessful) {
                        val result = response.body()?.byteStream()
                        result?.let {
                            writeToFile(it, pdfFileName)
                        }?:kotlin.run {
                            isFileReadyObserver.postValue(false)
                        }
                    }
                    else
                        isFileReadyObserver.postValue(false)
                }
            })
        }
    }

    private fun writeToFile(inputStream: InputStream, pdfFileName : File) {
        try {
            Log.e("====", "====writeToFile : " )
            val fileReader = ByteArray(4096)
            var fileSizeDownloaded = 0
            val fos: OutputStream = FileOutputStream(pdfFileName)
            do {
                val read = inputStream.read(fileReader)
                if (read != -1) {
                    fos.write(fileReader, 0, read)
                    fileSizeDownloaded += read
                }
            } while (read != -1)
            fos.flush()
            fos.close()
            isFileReadyObserver.postValue(true)
        }catch ( e: IOException) {
            Log.e("====", "====IOException : "+e )
            isFileReadyObserver.postValue(false)
        }
    }

}