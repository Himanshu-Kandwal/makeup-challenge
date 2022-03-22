package md.absa.makeup.challenge.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import md.absa.makeup.challenge.model.ProductColor
import java.lang.reflect.Type

class ProductColorConverter {

    @TypeConverter
    fun toString(productColors: ArrayList<ProductColor?>?): String {
        val gson = Gson()
        return gson.toJson(productColors)
    }

    @TypeConverter
    fun fromString(string: String?): ArrayList<ProductColor> {
        val listType: Type = object : TypeToken<ArrayList<ProductColor?>?>() {}.type
        return Gson().fromJson(string, listType)
    }
}
