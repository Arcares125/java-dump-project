package com.stockmarket.app.security;

import org.springframework.stereotype.Component;

/**
 * >>>>>>>>>>>
 * SUPPLEMENTARY FOR QUIZ 13: JWT Authentication Filter
 * 
 * Your task:
 * Implement a JWT authentication filter that:
 * 1. Extracts the JWT from the request header
 * 2. Validates the token
 * 3. Sets the authentication in the security context
 * 
 * HINTS:
 * 1. Extend the OncePerRequestFilter class:
 *    public class JwtAuthenticationFilter extends OncePerRequestFilter {
 *        // Implementation
 *    }
 * 
 * 2. Inject required dependencies:
 *    private final JwtTokenProvider tokenProvider;
 *    private final UserDetailsService userDetailsService;
 *    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
 *    
 *    public JwtAuthenticationFilter(
 *            JwtTokenProvider tokenProvider,
 *            UserDetailsService userDetailsService) {
 *        this.tokenProvider = tokenProvider;
 *        this.userDetailsService = userDetailsService;
 *    }
 * 
 * 3. Override the doFilterInternal method:
 *    @Override
 *    protected void doFilterInternal(
 *            HttpServletRequest request,
 *            HttpServletResponse response,
 *            FilterChain filterChain) throws ServletException, IOException {
 *        
 *        try {
 *            String jwt = getJwtFromRequest(request);
 *            
 *            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
 *                String username = tokenProvider.getUsernameFromToken(jwt);
 *                
 *                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
 *                UsernamePasswordAuthenticationToken authentication =
 *                    new UsernamePasswordAuthenticationToken(
 *                        userDetails, null, userDetails.getAuthorities());
 *                    
 *                authentication.setDetails(
 *                    new WebAuthenticationDetailsSource().buildDetails(request));
 *                    
 *                SecurityContextHolder.getContext().setAuthentication(authentication);
 *            }
 *        } catch (Exception ex) {
 *            logger.error("Could not set user authentication in security context", ex);
 *        }
 *        
 *        filterChain.doFilter(request, response);
 *    }
 * 
 * 4. Implement a helper method to extract the JWT from the request:
 *    private String getJwtFromRequest(HttpServletRequest request) {
 *        String bearerToken = request.getHeader("Authorization");
 *        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
 *            return bearerToken.substring(7);
 *        }
 *        return null;
 *    }
 * 
 * 5. Add necessary imports:
 *    import org.slf4j.Logger;
 *    import org.slf4j.LoggerFactory;
 *    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 *    import org.springframework.security.core.context.SecurityContextHolder;
 *    import org.springframework.security.core.userdetails.UserDetails;
 *    import org.springframework.security.core.userdetails.UserDetailsService;
 *    import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
 *    import org.springframework.util.StringUtils;
 *    import org.springframework.web.filter.OncePerRequestFilter;
 *    import javax.servlet.FilterChain;
 *    import javax.servlet.ServletException;
 *    import javax.servlet.http.HttpServletRequest;
 *    import javax.servlet.http.HttpServletResponse;
 *    import java.io.IOException;
 * 
 * >>>>>>>>>>>
 */
@Component
public class JwtAuthenticationFilter {
    
    // TODO: Implement your solution here
    
} 