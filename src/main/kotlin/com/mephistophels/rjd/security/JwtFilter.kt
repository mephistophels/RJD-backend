package com.mephistophels.rjd.security

import com.mephistophels.rjd.errors.ApiError
//import com.mephistophels.rjd.errors.ExceptionResolver
import com.mephistophels.rjd.util.API_PUBLIC
import com.mephistophels.rjd.util.containsAnyPath
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    @Lazy
    private val jwtParser: JwtParser,
//    private val exceptionResolver: ExceptionResolver,
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.requestURI.containsAnyPath(
            API_PUBLIC,
        )
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val header = request.getHeader("Authorization")
                ?: throw ApiError(HttpStatus.UNAUTHORIZED, "Вы не авторизованы","Authorization header not found")
            SecurityContextHolder.getContext().authentication = jwtParser.createAuthToken(header)
            filterChain.doFilter(request, response)
        } catch (exception: ApiError) {
//            exceptionResolver.resolveException(request, response, exception)
        }
    }
}
