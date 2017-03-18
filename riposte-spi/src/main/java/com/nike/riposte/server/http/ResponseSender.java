package com.nike.riposte.server.http;

import com.nike.riposte.server.error.handler.ErrorResponseBody;
import io.netty.channel.ChannelHandlerContext;

/**
 * Responsible for sending the response to the client. Will populate a {@link TraceHeaders#TRACE_ID} trace header for
 * all outgoing responses, correctly serializes and sends response body content (along with all the appropriate
 * content-related headers such as {@link io.netty.handler.codec.http.HttpHeaders.Names#CONTENT_TYPE}), and correctly
 * handles keep-alive connections. Contains methods for both full responses and chunked responses.
 * <p/>
 * Non-error full responses should call {@link #sendFullResponse(io.netty.channel.ChannelHandlerContext, RequestInfo,
 * ResponseInfo)} or {@link #sendFullResponse(io.netty.channel.ChannelHandlerContext, RequestInfo, ResponseInfo,
 * com.fasterxml.jackson.databind.ObjectMapper)}. Error responses should call {@link
 * #sendErrorResponse(io.netty.channel.ChannelHandlerContext, RequestInfo, ResponseInfo)}. Chunked responses should call
 * {@link #sendResponseChunk(ChannelHandlerContext, RequestInfo, ResponseInfo, ChunkedOutboundMessage)}.
 *
 * @author Nic Munroe
 */
public interface ResponseSender {
    void sendResponseChunk(ChannelHandlerContext ctx,
                           RequestInfo<?> requestInfo, ResponseInfo<?> responseInfo,
                           Object msg);

    void sendFullResponse(ChannelHandlerContext ctx,
                          RequestInfo requestInfo, ResponseInfo<?> responseInfo) throws Exception;

    void sendErrorResponse(ChannelHandlerContext ctx,
                           RequestInfo requestInfo,
                           ResponseInfo<ErrorResponseBody> responseInfo) throws Exception;
}
