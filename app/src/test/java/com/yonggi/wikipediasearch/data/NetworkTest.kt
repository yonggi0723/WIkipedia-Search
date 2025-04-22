import com.yonggi.customhttp.ConnectionFactory
import com.yonggi.customhttp.HttpCustomBuilder
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.net.HttpURLConnection
import java.net.URL

class NetworkClientTest {

    // 실제 HttpURLConnection을 흉내내는 Stub 클래스
    class FakeHttpURLConnection : HttpURLConnection(URL("https://test.com")) {
        override fun connect() {}
        override fun disconnect() {}
        override fun usingProxy(): Boolean = false
        override fun getInputStream() = ByteArrayInputStream("mock-response".toByteArray())
        override fun getOutputStream() = java.io.ByteArrayOutputStream()
    }

    // HttpCustomBuilder 내부에서 사용할 수 있게 Factory도 스텁
    class FakeConnectionFactory : ConnectionFactory {
        override fun open(url: String): HttpURLConnection {
            return FakeHttpURLConnection()
        }
    }

    @Test
    fun getTest() {
        val builder = HttpCustomBuilder.builder()
            .baseurl("https://test.com")
            .connectionFactory(FakeConnectionFactory())
            .build()

        val response = builder.get("/test-path")
        assertEquals("mock-response", response)
    }

    @Test
    fun postTest() {
        val builder = HttpCustomBuilder.builder()
            .baseurl("https://test.com")
            .connectionFactory(FakeConnectionFactory())
            .build()

        val response = builder.post("/submit", mapOf("key" to "value"))
        assertEquals("mock-response", response)
    }

    @Test
    fun putTest() {
        val builder = HttpCustomBuilder.builder()
            .baseurl("https://test.com")
            .connectionFactory(FakeConnectionFactory())
            .build()

        val response = builder.put("/update", mapOf("key" to "value"))
        assertEquals("mock-response", response)
    }

    @Test
    fun deleteTest() {
        val builder = HttpCustomBuilder.builder()
            .baseurl("https://test.com")
            .connectionFactory(FakeConnectionFactory())
            .build()

        val response = builder.delete("/remove")
        assertEquals("mock-response", response)
    }
}