// Generated from Xquery.g4 by ANTLR 4.7.2

package edu.ucsd.cse232b.Antlr4Xquery;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XqueryParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XqueryVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code singleAP}
	 * labeled alternative in {@link XqueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleAP(XqueryParser.SingleAPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleAP}
	 * labeled alternative in {@link XqueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleAP(XqueryParser.DoubleAPContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#doc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoc(XqueryParser.DocContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceRP(XqueryParser.BraceRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleSlashRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleSlashRP(XqueryParser.DoubleSlashRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code textRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextRP(XqueryParser.TextRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code attRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttRP(XqueryParser.AttRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentRP(XqueryParser.ParentRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfRP(XqueryParser.SelfRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterRP(XqueryParser.FilterRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commaRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommaRP(XqueryParser.CommaRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChildrenRP(XqueryParser.ChildrenRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tagRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagRP(XqueryParser.TagRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleSlashRP}
	 * labeled alternative in {@link XqueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleSlashRP(XqueryParser.SingleSlashRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqFilter(XqueryParser.EqFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotFilter(XqueryParser.NotFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndFilter(XqueryParser.AndFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsFilter(XqueryParser.IsFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpFilter(XqueryParser.RpFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringFilter(XqueryParser.StringFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceFilter(XqueryParser.BraceFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XqueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrFilter(XqueryParser.OrFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FLWR}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFLWR(XqueryParser.FLWRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleSlashXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleSlashXQ(XqueryParser.SingleSlashXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tagXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagXQ(XqueryParser.TagXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApXQ(XqueryParser.ApXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code letXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetXQ(XqueryParser.LetXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringXQ(XqueryParser.StringXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commaXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommaXQ(XqueryParser.CommaXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarXQ(XqueryParser.VarXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceXQ(XqueryParser.BraceXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleSlashXQ}
	 * labeled alternative in {@link XqueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleSlashXQ(XqueryParser.DoubleSlashXQContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#forClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForClause(XqueryParser.ForClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#letClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetClause(XqueryParser.LetClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(XqueryParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#returnClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnClause(XqueryParser.ReturnClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parSatisfyCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParSatisfyCond(XqueryParser.ParSatisfyCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrCond(XqueryParser.OrCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breaceCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreaceCond(XqueryParser.BreaceCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyCond(XqueryParser.EmptyCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndCond(XqueryParser.AndCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsCond(XqueryParser.IsCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqCond(XqueryParser.EqCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notCond}
	 * labeled alternative in {@link XqueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotCond(XqueryParser.NotCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XqueryParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#attName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttName(XqueryParser.AttNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#fileName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFileName(XqueryParser.FileNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(XqueryParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#openTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpenTag(XqueryParser.OpenTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link XqueryParser#closeTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloseTag(XqueryParser.CloseTagContext ctx);
}