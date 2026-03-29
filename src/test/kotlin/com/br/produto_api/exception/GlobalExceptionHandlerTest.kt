package com.br.produto_api.exception

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.ServletWebRequest
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()

    @Test
    fun `deve processar exception genérica e retornar ErrorResponse`() {
        val exception = Exception("Erro de teste")
        val request = MockHttpServletRequest()
        val webRequest = ServletWebRequest(request)

        val response = handler.handleGlobalException(exception, webRequest)

        assertEquals(500, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(500, response.body!!.status)
        assertEquals("Erro Interno", response.body!!.error)
        assertEquals("Erro de teste", response.body!!.message)
    }

    @Test
    fun `deve retornar mensagem padrao quando exception nao tem mensagem`() {
        val exception = Exception()
        val request = MockHttpServletRequest()
        val webRequest = ServletWebRequest(request)

        val response = handler.handleGlobalException(exception, webRequest)

        assertNotNull(response.body)
        assertEquals("Erro desconhecido", response.body!!.message)
    }

    @Test
    fun `deve criar ErrorResponse com timestamp`() {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = 404,
            error = "Not Found",
            message = "Recurso nao encontrado",
            path = "/api/produtos/999"
        )

        assertEquals(404, errorResponse.status)
        assertEquals("Not Found", errorResponse.error)
        assertEquals("Recurso nao encontrado", errorResponse.message)
        assertEquals("/api/produtos/999", errorResponse.path)
        assertNotNull(errorResponse.timestamp)
    }
}

