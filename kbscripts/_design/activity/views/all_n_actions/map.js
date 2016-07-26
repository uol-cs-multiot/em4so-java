function (doc) {
	var theInput = null;
	if (doc.type	&& doc.type == 'activity') {
		if (doc.input.operands) theInput = {operands:doc.input.operands, operator:doc.input.operator};
		else theInput = {operand1 : doc.input.operand1, operand2: doc.input.operand2, operator: doc.input.operator}
		emit(doc._id,
				{
					inputKnowledge : doc.input.knowledge,
					input: theInput,
					output: doc.output,
					description : doc.description,
					categories : doc.categories
				});
		if (doc.actions)
			doc.actions
					.forEach(function(
							action) {
						emit(
								doc._id,
								{
									id:action.id,
									prereq:action.prereq,
									name : action.name,
									args : action.args,
									result : action.result
								});
					});
	}
}