/*
 * Sibilla:  a Java framework designed to support analysis of Collective
 * Adaptive Systems.
 *
 *             Copyright (C) 2020.
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package it.unicam.quasylab.sibilla.langs.yoda;

import it.unicam.quasylab.sibilla.langs.util.ErrorCollector;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.function.Function;

public class TypeVisitor extends YodaModelBaseVisitor<DataType>{

    private final Function<String, DataType> table;
    private final ErrorCollector errorCollector;

    public TypeVisitor(Function<String, DataType> table, ErrorCollector errorCollector) {
        this.table = table;
        this.errorCollector= errorCollector;
    }

    public static DataType getTypeOf (ErrorCollector errorCollector, Function<String, DataType> types, ParserRuleContext ctx){
        return ctx.accept(new TypeVisitor(types,errorCollector));
    }

    public boolean checkType(DataType expected, YodaModelParser.ExprContext argument){
        DataType actual = argument.accept(this);
        if (!actual.isSubtypeOf(expected)){
            errorCollector.record(ParseUtil.wrongTypeError(expected, actual, argument));
            return false;
        }else{
            return true;
        }
    }

    private DataType checkNumber(YodaModelParser.ExprContext exprContext) {
        DataType type = exprContext.accept(this);
        if (!type.isANumber()){
            errorCollector.record(ParseUtil.expectedNumberError(type, exprContext));
            return DataType.INTEGER;
        }
        return type;
    }

    @Override
    public DataType visitNegationExpression(YodaModelParser.NegationExpressionContext ctx){
        checkType(DataType.BOOLEAN, ctx.argument);
        return DataType.BOOLEAN;
    }

    @Override
    public DataType visitExprBrackets(YodaModelParser.ExprBracketsContext ctx){
            return ctx.expr().accept(this);
    }

    @Override
    public DataType visitWeightedRandomExpression(YodaModelParser.WeightedRandomExpressionContext ctx){ return DataType.REAL; }

    @Override
    public DataType visitFalse(YodaModelParser.FalseContext ctx){
        return DataType.BOOLEAN;
    }

    //TODO
    @Override
    public DataType visitMinimumExpression(YodaModelParser.MinimumExpressionContext ctx){
        return null;
    }

    //TODO
    @Override
    public DataType visitMaximumExpression(YodaModelParser.MaximumExpressionContext ctx){
        return null;
    }

    @Override
    public DataType visitMultdivOperation(YodaModelParser.MultdivOperationContext ctx) {
        DataType t1 = checkNumber(ctx.leftOp);
        DataType t2 = checkNumber(ctx.rightOp);
        return DataType.merge(t1,t2);
    }

    @Override
    public DataType visitRelationExpression(YodaModelParser.RelationExpressionContext ctx) {
        checkNumber(ctx.leftOp);
        checkNumber(ctx.rightOp);
        return DataType.BOOLEAN;
    }

    //TODO
    @Override
    public DataType visitReference(YodaModelParser.ReferenceContext ctx) {
        return null;
    }

    //TODO
    @Override
    public DataType visitRecordExpression(YodaModelParser.RecordExpressionContext ctx) {
        return null;
    }

    @Override
    public DataType visitOrExpression(YodaModelParser.OrExpressionContext ctx) {
        checkType(DataType.BOOLEAN, ctx.leftOp);
        checkType(DataType.BOOLEAN, ctx.rightOp);
        return DataType.BOOLEAN;
    }

    @Override
    public DataType visitExponentOperation(YodaModelParser.ExponentOperationContext ctx) {
        DataType t1 = checkNumber(ctx.leftOp);
        DataType t2 = checkNumber(ctx.rightOp);
        return DataType.merge(t1,t2);
    }

    @Override
    public DataType visitRealValue(YodaModelParser.RealValueContext ctx) {return DataType.REAL;}

    @Override
    public DataType visitAndExpression(YodaModelParser.AndExpressionContext ctx) {
        checkType(DataType.BOOLEAN, ctx.leftOp);
        checkType(DataType.BOOLEAN, ctx.rightOp);
        return DataType.BOOLEAN;
    }

    @Override
    public DataType visitAdditionalOperation(YodaModelParser.AdditionalOperationContext ctx) {
        DataType t1 = checkNumber(ctx.leftOp);
        DataType t2 = checkNumber(ctx.rightOp);
        return DataType.merge(t1,t2);
    }

    //TODO
    @Override
    public DataType visitForallExpression(YodaModelParser.ForallExpressionContext ctx) {
        return null;
    }

    //TODO
    @Override
    public DataType visitExistsExpression(YodaModelParser.ExistsExpressionContext ctx) {
        return null;
    }

    @Override
    public DataType visitTrue(YodaModelParser.TrueContext ctx) {return DataType.BOOLEAN;}

    @Override
    public DataType visitAddsubOperation(YodaModelParser.AddsubOperationContext ctx) {
        DataType t1 = checkNumber(ctx.leftOp);
        DataType t2 = checkNumber(ctx.rightOp);
        return DataType.merge(t1, t2);
    }

    @Override
    public DataType visitIntegerValue(YodaModelParser.IntegerValueContext ctx) {return DataType.INTEGER;}

    @Override
    public DataType visitIfthenelseExpression(YodaModelParser.IfthenelseExpressionContext ctx) {
        checkType(DataType.BOOLEAN, ctx.guardExpr);
        return DataType.merge(ctx.thenBranch.accept(this), ctx.elseBranch.accept(this));
    }

    @Override
    public DataType visitRandomExpression(YodaModelParser.RandomExpressionContext ctx) {return DataType.REAL;}

    //TODO
    @Override
    public DataType visitItselfRef(YodaModelParser.ItselfRefContext ctx) {
        return null;
    }

    //TODO
    @Override
    public DataType visitAttributeRef(YodaModelParser.AttributeRefContext ctx) {
        return null;
    }
}
