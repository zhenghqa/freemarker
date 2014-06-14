/*
 * Copyright 2014 Attila Szegedi, Daniel Dekany, Jonathan Revusky
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package freemarker.ext.beans;

import java.lang.reflect.Method;
import java.util.List;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;
import freemarker.template.utility.ClassUtil;
import freemarker.template.utility.Collections12;

/**
 * A class that will wrap a reflected method call into a
 * {@link freemarker.template.TemplateMethodModel} interface. 
 * It is used by {@link BeanModel} to wrap reflected method calls
 * for non-overloaded methods.
 * @author Attila Szegedi, szegedia at users dot sourceforge dot net
 */
public final class SimpleMethodModel extends SimpleMethod
    implements
    TemplateMethodModelEx,
    TemplateSequenceModel
{
    private final Object object;
    private final BeansWrapper wrapper;

    /**
     * Creates a model for a specific method on a specific object.
     * @param object the object to call the method on. Can be
     * <tt>null</tt> for static methods.
     * @param method the method that will be invoked.
     */
    SimpleMethodModel(Object object, Method method, Class[] argTypes, 
            BeansWrapper wrapper)
    {
        super(method, argTypes);
        this.object = object;
        this.wrapper = wrapper;
    }

    /**
     * Invokes the method, passing it the arguments from the list.
     */
    public Object exec(List arguments)
        throws
        TemplateModelException
    {
        try
        {
            return wrapper.invokeMethod(object, (Method)getMember(), 
                    unwrapArguments(arguments, wrapper));
        }
        catch(TemplateModelException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            if (e instanceof TemplateModelException) throw (TemplateModelException) e;
            throw _MethodUtil.newInvocationTemplateModelException(object, getMember(), e);
        }
    }
    
    public TemplateModel get(int index) throws TemplateModelException
    {
        return (TemplateModel) exec(Collections12.singletonList(
                new SimpleNumber(new Integer(index))));
    }

    public int size() throws TemplateModelException {
        throw new TemplateModelException(
                "Getting the number of items or enumerating the items is not supported on this "
                + ClassUtil.getFTLTypeDescription(this) + " value.\n"
                + "("
                + "Hint 1: Maybe you wanted to call this method first and then do something with its return value. "
                + "Hint 2: Getting items by intex possibly works, hence it's a \"+sequence\"."
                + ")");
    }
    
    public String toString() {
        return getMember().toString();
    }
}