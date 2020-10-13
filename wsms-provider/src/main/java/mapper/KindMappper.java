package mapper;

import entity.Kind;

import java.util.List;

public interface KindMappper {
    public List<Kind> getAllKind();
    public List<Kind> getKindByName(String name);
    public void addKind(String name);
}
