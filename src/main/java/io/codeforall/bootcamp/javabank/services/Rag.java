package io.codeforall.bootcamp.javabank.services;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.openai.OpenAiEmbeddingClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class Rag {
    @Value("classpath:/ai/rag/*")
    private List<Resource> ragDocuments;

    File store = new File("/tmp/vector_store.json");

    private SimpleVectorStore vectorStore;

    public void setVectorStore(SimpleVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rag")
    public void init() {


        ragDocuments.forEach(resource -> {
            System.out.println(resource.getFilename());

            // EXTRACT
            TikaDocumentReader documentReader = new TikaDocumentReader(resource);
            List<Document> docs = documentReader.get();

            // TRANSFORM
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> splitDocs = textSplitter.apply(docs);

            // Load
            vectorStore.add(splitDocs);
        });
        vectorStore.save(store);
    }
    @RequestMapping
    public List<String> search(String question) {

        // Build a query to retrieve the 5 most relevant documents for our question
        SearchRequest query = SearchRequest.query(question).withTopK(5);

        // Fetch the documents from the store
        List<Document> documents =  vectorStore.similaritySearch(query);

        // Extract the contents of each document
        return documents.stream().map(Document::getContent).toList();

    }
}