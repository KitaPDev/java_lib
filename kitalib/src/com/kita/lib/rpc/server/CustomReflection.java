package com.kita.lib.rpc.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CustomReflection {

    private CustomReflection() {}

    public static Object getDefaultInstance(Class p_class) throws IllegalAccessException, InstantiationException {
        Object objInstance = p_class.newInstance();

        return objInstance;
    }

    public static List<Method> getMethods(Object p_objHandler) {
        Class classHandler = p_objHandler.getClass();
        Method[] methods = classHandler.getMethods();
        List<Method> lsOurMethods = new ArrayList<>();

        for(Method method : methods) {
            Class classDeclaring = method.getDeclaringClass();

            if(classHandler.getCanonicalName().compareTo(classDeclaring.getCanonicalName()) == 0) {
                lsOurMethods.add(method);
            }
        }

        return lsOurMethods;
    }

    public static Object executeMethod(String p_strClassName, String p_strMethodName, List p_lsMethodParameters) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {

        try {
            Class handledClass = RPCServer.getHandler(p_strClassName);
            List<Class> lsMethodParameterClasses = new ArrayList<>();

            for(int i = 0; i < p_lsMethodParameters.size(); i++) {
                lsMethodParameterClasses.add(p_lsMethodParameters.get(i).getClass());
            }

            Method method = null;
            Object objReturn;

            if(lsMethodParameterClasses.size() == 1) {
                method = handledClass.getDeclaredMethod(p_strMethodName, lsMethodParameterClasses.get(0));

                if(!method.getReturnType().getName().equals("void")) {
                    objReturn = method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0));
                    return objReturn;
                }

                method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0));

            } else if(lsMethodParameterClasses.size() == 2) {
                method = handledClass.getDeclaredMethod(p_strMethodName, lsMethodParameterClasses.get(0),
                        lsMethodParameterClasses.get(1));

                if(!method.getReturnType().getName().equals("void")) {
                    objReturn = method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                            p_lsMethodParameters.get(1));
                    return objReturn;
                }

                method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                        p_lsMethodParameters.get(1));

            } else if(lsMethodParameterClasses.size() == 3) {
                method = handledClass.getDeclaredMethod(p_strMethodName, lsMethodParameterClasses.get(0),
                        lsMethodParameterClasses.get(1), lsMethodParameterClasses.get(2));

                if(!method.getReturnType().getName().equals("void")) {
                    objReturn = method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                            p_lsMethodParameters.get(1), p_lsMethodParameters.get(2));
                    return objReturn;
                }

                method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                        p_lsMethodParameters.get(1), p_lsMethodParameters.get(2));

            } else if(lsMethodParameterClasses.size() == 4) {
                method = handledClass.getDeclaredMethod(p_strMethodName, lsMethodParameterClasses.get(0),
                        lsMethodParameterClasses.get(1), lsMethodParameterClasses.get(2), lsMethodParameterClasses.get(3));

                if(!method.getReturnType().getName().equals("void")) {
                    objReturn = method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                            p_lsMethodParameters.get(1), p_lsMethodParameters.get(2),p_lsMethodParameters.get(3));
                    return objReturn;
                }

                method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                        p_lsMethodParameters.get(1), p_lsMethodParameters.get(2), p_lsMethodParameters.get(3));

            } else if(lsMethodParameterClasses.size() == 5) {
                method = handledClass.getDeclaredMethod(p_strMethodName, lsMethodParameterClasses.get(0),
                        lsMethodParameterClasses.get(1), lsMethodParameterClasses.get(2), lsMethodParameterClasses.get(3),
                        lsMethodParameterClasses.get(4));

                if(!method.getReturnType().getName().equals("void")) {
                    objReturn = method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                            p_lsMethodParameters.get(1), p_lsMethodParameters.get(2), p_lsMethodParameters.get(3),
                            p_lsMethodParameters.get(4));
                    return objReturn;
                }

                method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                        p_lsMethodParameters.get(1), p_lsMethodParameters.get(2), p_lsMethodParameters.get(3),
                        p_lsMethodParameters.get(4));

            } else if(lsMethodParameterClasses.size() == 6) {
                method = handledClass.getDeclaredMethod(p_strMethodName, lsMethodParameterClasses.get(0),
                        lsMethodParameterClasses.get(1), lsMethodParameterClasses.get(2), lsMethodParameterClasses.get(3),
                        lsMethodParameterClasses.get(4), lsMethodParameterClasses.get(5));

                if(!method.getReturnType().getName().equals("void")) {
                    objReturn = method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                            p_lsMethodParameters.get(1), p_lsMethodParameters.get(2), p_lsMethodParameters.get(3),
                            p_lsMethodParameters.get(4), p_lsMethodParameters.get(5));
                    return objReturn;
                }

                method.invoke(CustomReflection.getDefaultInstance(handledClass), p_lsMethodParameters.get(0),
                        p_lsMethodParameters.get(1), p_lsMethodParameters.get(2), p_lsMethodParameters.get(3),
                        p_lsMethodParameters.get(4), p_lsMethodParameters.get(5));
            }

        } catch (NoSuchMethodException e) {
            System.out.println();
            e.printStackTrace();
        }
        return null;
    }
}
