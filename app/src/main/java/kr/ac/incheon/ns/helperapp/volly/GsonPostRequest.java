package kr.ac.incheon.ns.helperapp.volly;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonPostRequest<T> extends Request<T> {
    private final Gson mGson;
	private final Class<T> clazz;
	private final Map<String, String> headers;
	private final Map<String, String> params;
	private final Listener<T> listener;
 
	/**
	 * Make a request and return a parsed object from JSON.
	 * 
	 * @param method
	 *            Method.GET, Method.POST, and so on
	 * @param url
	 *            URL of the request to make
	 * @param clazz
	 *            Relevant class object, for Gson's reflection
     	 * @param params
	 *            Map of request params
	 * @param headers
	 *            Map of request headers
	 */
	public GsonPostRequest(int method, String url, Class<T> clazz, Map<String, String> params, Map<String, String> headers,
						   Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.clazz = clazz;
		this.params = params;
		this.headers = headers;
		this.listener = listener;
		mGson = new Gson();
	}

	@Override
    	protected Map<String, String> getParams() throws AuthFailureError {
        	return params != null ? params : super.getParams();
    	}
 
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}
 
	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}
 
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,	HttpHeaderParser.parseCharset(response.headers));
			return Response.success(mGson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}