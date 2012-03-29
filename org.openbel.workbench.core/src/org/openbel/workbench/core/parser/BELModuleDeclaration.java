package org.openbel.workbench.core.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.IElementRequestor.FieldInfo;

public class BELModuleDeclaration extends ModuleDeclaration {

    private List<FunctionInfo> functionsInfo;
    private List<FieldInfo> variablesInfo;

    public BELModuleDeclaration(int sourceLength) {
        super(sourceLength);
        functionsInfo = new ArrayList<FunctionInfo>();
        variablesInfo = new ArrayList<FieldInfo>();
    }

    public List<FieldInfo> getFieldsInfo() {
        return variablesInfo;
    }

    public List<FunctionInfo> getFunctionsInfo() {
        return functionsInfo;
    }

    @SuppressWarnings("unchecked")
    public void setFunctions(List<MethodDeclaration> functions) {
        getFunctionList().addAll(functions);
        for (MethodDeclaration method : functions) {
            FunctionInfo mInfo = new FunctionInfo();
            mInfo.name = method.getName();
            mInfo.nameSourceStart = method.getNameStart();
            mInfo.nameSourceEnd = method.getNameEnd();
            mInfo.declarationStart = method.sourceStart();
            mInfo.declarationEnd = method.sourceEnd();
            functionsInfo.add(mInfo);
        }
    }

    @SuppressWarnings("unchecked")
    public void setVariables(List<FieldDeclaration> variables) {
        getVariablesList().addAll(variables);
        for (FieldDeclaration method : variables) {
            FieldInfo vInfo = new FieldInfo();
            vInfo.name = method.getName();
            vInfo.nameSourceStart = method.getNameStart();
            vInfo.nameSourceEnd = method.getNameEnd();
            vInfo.declarationStart = method.sourceStart();
            variablesInfo.add(vInfo);
        }
    }
}