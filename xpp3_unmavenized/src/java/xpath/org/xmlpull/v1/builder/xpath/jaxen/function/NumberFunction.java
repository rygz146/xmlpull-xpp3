/*
 * $Header: /l/extreme/cvspub/XPP3/java/src/java/xpath/org/xmlpull/v1/builder/xpath/jaxen/function/NumberFunction.java,v 1.1 2004/06/16 15:55:40 aslom Exp $
 * $Revision: 1.1 $
 * $Date: 2004/06/16 15:55:40 $
 *
 * ====================================================================
 *
 * Copyright (C) 2000-2002 bob mcwhirter & James Strachan.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions, and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions, and the disclaimer that follows 
 *    these conditions in the documentation and/or other materials 
 *    provided with the distribution.
 *
 * 3. The name "Jaxen" must not be used to endorse or promote products
 *    derived from this software without prior written permission.  For
 *    written permission, please contact license@jaxen.org.
 * 
 * 4. Products derived from this software may not be called "Jaxen", nor
 *    may "Jaxen" appear in their name, without prior written permission
 *    from the Jaxen Project Management (pm@jaxen.org).
 * 
 * In addition, we request (but do not require) that you include in the 
 * end-user documentation provided with the redistribution and/or in the 
 * software itself an acknowledgement equivalent to the following:
 *     "This product includes software developed by the
 *      Jaxen Project (http://www.jaxen.org/)."
 * Alternatively, the acknowledgment may be graphical using the logos 
 * available at http://www.jaxen.org/
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE Jaxen AUTHORS OR THE PROJECT
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * ====================================================================
 * This software consists of voluntary contributions made by many 
 * individuals on behalf of the Jaxen Project and was originally 
 * created by bob mcwhirter <bob@werken.com> and 
 * James Strachan <jstrachan@apache.org>.  For more information on the 
 * Jaxen Project, please see <http://www.jaxen.org/>.
 * 
 * $Id: NumberFunction.java,v 1.1 2004/06/16 15:55:40 aslom Exp $
 */

package org.xmlpull.v1.builder.xpath.jaxen.function;

import org.xmlpull.v1.builder.xpath.jaxen.Context;
import org.xmlpull.v1.builder.xpath.jaxen.Function;
import org.xmlpull.v1.builder.xpath.jaxen.FunctionCallException;
import org.xmlpull.v1.builder.xpath.jaxen.Navigator;

import java.util.List;
import java.util.Iterator;

/**
 * <p><b>4.4</b> <code><i>number</i> number(<i>object</i>)</code> 
 * 
 * @author bob mcwhirter (bob @ werken.com)
 */
public class NumberFunction implements Function
  {
  private final static Double NaN = new Double( Double.NaN );
    
  public Object call(Context context, List args) throws FunctionCallException
    {
    if (args.size() == 1)
      {
      return evaluate( args.get(0), context.getNavigator() );
      }

    throw new FunctionCallException( "number() requires one argument." );
    }

  public static Double evaluate(Object obj, Navigator nav)
    {
    if( obj instanceof Double )
      {
      return (Double) obj;
      }
    else if (obj instanceof Number)
      {
      return new Double( ((Number) obj).doubleValue() );            
      }
    else if ( obj instanceof Boolean )
      {
      if ( obj == Boolean.TRUE )
        {
        return new Double( 1 );
        }
      else
        {
        return new Double( 0 );
        }
      }
    else if ( obj instanceof String )
      {
      String str = (String) obj;
      
      try
        {
        Double doubleValue = new Double( str );        
        return doubleValue;
        }
      catch (NumberFormatException e)
        {
        return new Double( Double.NaN );
        }
      }
    else if ( obj instanceof List || obj instanceof Iterator )
      {
      String strValue = StringFunction.evaluate( obj, nav );
      return evaluate( StringFunction.evaluate( obj, nav ), nav );
      }
    else if ( nav.isElement( obj ) || nav.isAttribute( obj ) )
      {
      return evaluate( StringFunction.evaluate( obj, nav ), nav );
      }
    
    return new Double( Double.NaN );
    }
  
  public static boolean isNaN( double val )
    {
    return isNaN( new Double( val ) );
    }
  
  public static boolean isNaN( Double val )
    {
    return val.equals( NaN );
    }    
  }
