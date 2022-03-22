package md.absa.makeup.challenge.data.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import md.absa.makeup.challenge.data.db.MakeUpDatabase
import md.absa.makeup.challenge.getOrAwaitValue
import md.absa.makeup.challenge.model.MakeUpItem
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class MakeUpItemDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: MakeUpDatabase
    private lateinit var makeUpItemDao: MakeUpItemDao

    @Before
    fun setup() {
        hiltRule.inject()
        makeUpItemDao = database.makeUpItemDao()
    }

    @After
    fun tear() {
        database.close()
    }

    @Test
    fun nukeTable() = runBlockingTest {

        val makeUpItem = Gson().fromJson(data, MakeUpItem::class.java)

        makeUpItemDao.insert(makeUpItem)

        makeUpItemDao.nukeTable()

        val products = makeUpItemDao.getAllMakeUpItems().getOrAwaitValue()

        assertThat(products).isEmpty()
    }

    @Test
    fun getProductsByProductType() = runBlockingTest {

        val makeUpItem = Gson().fromJson(data, MakeUpItem::class.java)

        makeUpItemDao.insert(makeUpItem)

        val products = makeUpItemDao.getProductsByProductType(makeUpItem.product_type!!).first()

        assertThat(products.product_type).isEqualTo(makeUpItem.product_type)
    }

    @Test
    fun getProductsById() = runBlockingTest {

        val makeUpItem = Gson().fromJson(data, MakeUpItem::class.java)

        makeUpItemDao.insert(makeUpItem)

        val product = makeUpItemDao.getProductById(makeUpItem.id.toString())

        assertThat(product.id).isEqualTo(makeUpItem.id)
    }

    @Test
    fun getProductsByBrand() = runBlockingTest {

        val makeUpItem = Gson().fromJson(data, MakeUpItem::class.java)

        makeUpItemDao.insert(makeUpItem)

        val products = makeUpItemDao.getProductsByBrand(makeUpItem.brand!!).asLiveData().getOrAwaitValue().first()

        assertThat(products.brand).isEqualTo(makeUpItem.brand)
    }

    @Test
    fun getBrands() = runBlockingTest {

        val makeUpItem = Gson().fromJson(data, MakeUpItem::class.java)

        makeUpItemDao.insert(makeUpItem)

        val allBrands = makeUpItemDao.getBrands().asLiveData().getOrAwaitValue()

        assertThat(allBrands).contains(makeUpItem.brand)
    }

    @Test
    fun insertMakeUpItem() = runBlockingTest {

        val makeUpItem = Gson().fromJson(data, MakeUpItem::class.java)

        makeUpItemDao.insert(makeUpItem)

        val allMakeUpItems = makeUpItemDao.getAllMakeUpItems().getOrAwaitValue()

        assertThat(allMakeUpItems).contains(makeUpItem)
    }

    @Test
    fun deleteMakeUpItem() = runBlockingTest {

        val makeUpItem = Gson().fromJson(data, MakeUpItem::class.java)

        makeUpItemDao.insert(makeUpItem)
        makeUpItemDao.delete(makeUpItem)

        val allMakeUpItems = makeUpItemDao.getAllMakeUpItems().getOrAwaitValue()

        assertThat(allMakeUpItems).doesNotContain(makeUpItem)
    }

    val data = " {\n" +
        "        \"localId\": 1,\n" +
        "        \"id\": 1047,\n" +
        "        \"brand\": \"colourpop\",\n" +
        "        \"name\": \"Blotted Lip\",\n" +
        "        \"price\": \"5.5\",\n" +
        "        \"price_sign\": \"\$\",\n" +
        "        \"currency\": \"CAD\",\n" +
        "        \"image_link\": \"https://cdn.shopify.com/s/files/1/1338/0845/products/brain-freeze_a_800x1200.jpg?v=1502255076\",\n" +
        "        \"product_link\": \"https://colourpop.com/collections/lippie-stix?filter=blotted-lip\",\n" +
        "        \"website_link\": \"https://colourpop.com\",\n" +
        "        \"description\": \"Blotted Lip Sheer matte lipstick that creates the perfect popsicle pout! Formula is lightweight, matte and buildable for light to medium coverage.\",\n" +
        "        \"rating\": null,\n" +
        "        \"category\": \"lipstick\",\n" +
        "        \"product_type\": \"lipstick\",\n" +
        "        \"tag_list\": [\n" +
        "            \"cruelty free\",\n" +
        "            \"Vegan\"\n" +
        "        ],\n" +
        "        \"created_at\": \"2018-07-08T22:01:20.178Z\",\n" +
        "        \"updated_at\": \"2018-07-09T00:53:23.287Z\",\n" +
        "        \"product_api_url\": \"http://makeup-api.herokuapp.com/api/v1/products/1047.json\",\n" +
        "        \"api_featured_image\": \"//s3.amazonaws.com/donovanbailey/products/api_featured_images/000/001/047/original/open-uri20180708-4-e7idod?1531087336\",\n" +
        "        \"product_colors\": [\n" +
        "            {\n" +
        "                \"hex_value\": \"#b72227\",\n" +
        "                \"colour_name\": \"Bee's Knees\"\n" +
        "            },\n" +
        "            {\n" +
        "                \"hex_value\": \"#BB656B\",\n" +
        "                \"colour_name\": \"Brain Freeze\"\n" +
        "            },\n" +
        "            {\n" +
        "                \"hex_value\": \"#8E4140\",\n" +
        "                \"colour_name\": \"Drip\"\n" +
        "            },\n" +
        "            {\n" +
        "                \"hex_value\": \"#A12A33\",\n" +
        "                \"colour_name\": \"On a Stick\"\n" +
        "            },\n" +
        "            {\n" +
        "                \"hex_value\": \"#904550\",\n" +
        "                \"colour_name\": \"Ice Cube\"\n" +
        "            },\n" +
        "            {\n" +
        "                \"hex_value\": \"#452222\",\n" +
        "                \"colour_name\": \"Lolly\"\n" +
        "            },\n" +
        "            {\n" +
        "                \"hex_value\": \"#7C3F35\",\n" +
        "                \"colour_name\": \"Candyfloss\"\n" +
        "            }\n" +
        "        ]\n" +
        "    }"
}
